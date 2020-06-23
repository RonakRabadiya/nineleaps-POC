package learn.rr.microservice.gatewayms.filter;

import com.netflix.ribbon.proxy.annotation.Http;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class RequestVersioningFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext.getCurrentContext().addZuulRequestHeader("API-VERSION",getVersion());
        return null;
    }

    private String getVersion(){
        String uri =  RequestContext.getCurrentContext().getRequest().getRequestURI();
        String versionPart ;
        if(uri.startsWith("/")){
            versionPart = uri.substring(uri.indexOf("/")+1,uri.indexOf("/",1));
        }
        else{
            versionPart = uri.substring(0,uri.indexOf("/",1));
        }

        switch(versionPart){
            case "v1" :
                return "1";
            case "v2":
                return "2";
        }
        return "";
    }
}
