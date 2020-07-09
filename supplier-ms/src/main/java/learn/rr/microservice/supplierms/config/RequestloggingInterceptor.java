package learn.rr.microservice.supplierms.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class RequestloggingInterceptor implements HandlerInterceptor {

    private  static final String CORRELATION_ID = "correlationId";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String correlationId = getCorrelationId(request);
        MDC.put(CORRELATION_ID,correlationId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(CORRELATION_ID);
    }

    private String getCorrelationId(HttpServletRequest request) {
      String correlationId=  request.getHeader(CORRELATION_ID);
      if(correlationId == null){
          correlationId = generateCorrelationId();
          log.info("correlation id is not found with header creating a new one. {}",correlationId);
      }else{
          log.info("found correlation id with header {}",correlationId);
      }
      return correlationId;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
