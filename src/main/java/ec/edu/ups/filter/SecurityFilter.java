package ec.edu.ups.filter;

import ec.edu.ups.pojo.InfoUsuario;
import ec.edu.ups.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

    public SecurityFilter() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            boolean notSesion = true;
            String reqURI = reqt.getRequestURI();
            if ( ses != null ) {
                InfoUsuario usuario = (InfoUsuario) ses.getAttribute(SessionUtil.INFO_USER);
                if (usuario != null){
                    notSesion = false;
                    if (reqURI.equals(reqt.getContextPath() + "/")) {
                        if (usuario.isEsCliente())
                            resp.sendRedirect(reqt.getContextPath() + "/virtual/");
                        else
                            resp.sendRedirect(reqt.getContextPath() + "/administrativo/");
                    }else if(reqURI.equals(reqt.getContextPath() + "/administrativo/login/")) {
                        if (usuario.isEsCliente()){
                            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }else {
                            resp.sendRedirect(reqt.getContextPath() + "/administrativo/");
                        }
                    }else if(reqURI.equals(reqt.getContextPath() + "/virtual/login/")) {
                        if (usuario.isEsCliente()){
                            resp.sendRedirect(reqt.getContextPath() + "/virtual/");
                        }else{
                            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }
                    }else if(reqURI.contains("/administrativo/")){
                        if (usuario.isEsCliente())
                            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }else if(reqURI.contains("/virtual/")){
                        if (!usuario.isEsCliente())
                            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
            if(notSesion){
                if (reqURI.contains("/administrativo/") && !reqURI.contains(reqt.getContextPath() + "/administrativo/login/")) {
                    resp.sendRedirect(reqt.getContextPath() + "/administrativo/login/");
                }else if (reqURI.contains("/virtual/") && !reqURI.contains(reqt.getContextPath() + "/virtual/login/")){
                    resp.sendRedirect(reqt.getContextPath() + "/virtual/login/");
                }
            }
            chain.doFilter(reqt, resp);
    }

    @Override
    public void destroy() {

    }
}