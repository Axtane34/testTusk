package ru.easybot.testTusk.config.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.util.utils.ExceptionFiller;
import ru.easybot.testTusk.util.wrappers.CachedBodyHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.StringJoiner;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    /**
     * Этот фильтр используется до оборачивания объекта HttpServletRequest в wrapper, поскольку исходный request,
     * блокирует исходный InputStream после первого прочтения, так же здесь логируются запросы к сервису и
     * перехватываются ошибки Spring для корректного отображения в json (по умолчанию они не доходят до обработчика,
     * а падают сразу в 500 стектрейс в консоль)
     * так же здесь оборачивается объект HttpServletRequest в wrapper до попадания в DispatcherServlet
     * @param filterChain обозначает цепочку фильтров, через которые проходят request и response до того как запрос попадает в DispatcherServlet
     * @throws ServletException выбрасывается методом .doFilter()
     * @throws IOException      выбрасывается методом getRequestInfo()
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            logger.info(getRequestInfo(cachedBodyHttpServletRequest));
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } catch (RuntimeException e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }

    /**
     * Метод перехватывает ошибки пойманные в фильтре, парсит json и стандартизирует для объекта ответа,
     * после чего пробрасывает их ниже до уровня обработчика
     *
     * @param status http статус код
     * @param ex     выбрасывается методом setMapBasedOnException()
     */
    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiResponse apiResponse = new ApiResponse(status.toString(), ex.getMessage(),
                ExceptionFiller.setMapBasedOnException(ex));
        try {
            response.getWriter().write(apiResponse.convertToJson());
            logger.error(apiResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод собирает и форматирует информацию из запроса для логирования
     *
     * @return возвращает собранный и отформатированный String с информацией о запросе
     */
    private String getRequestInfo(CachedBodyHttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nrequest:\n")
                .append("method: ").append(request.getMethod()).append("\n")
                .append("path: ").append(request.getServletPath()).append("\n")
                .append("headers:\n");
        if ("GET".equals(request.getMethod()) && request.getQueryString() != null) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            StringJoiner joiner = new StringJoiner("\n");
            sb.append("request parameters:\n");
            parameterMap.forEach((key, values) -> joiner.add(key + ": " + String.join(",", values)));
            sb.append(joiner).append("\n");
        }
        sb.append("request body:\n");
        String body = request.getReader().lines()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()
                .replaceAll(" {2}+", "")
                .replaceAll("}", "\n}")
                .replaceAll("\\{", "{\n")
                .replaceAll(",", ",\n")
                .replaceAll("\n{2}+", "");
        if (body.trim().length() == 0 || "GET".equals(request.getMethod())) {
            sb.append("nothing");
        } else {
            sb.append(body);
        }

        return sb.toString();
    }
}
