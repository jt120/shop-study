package itat.zttc.shop.web;

import javax.servlet.*;
import java.io.IOException;

public class CharacterFilter implements Filter {
	private String encoding;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(encoding);
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		encoding = cfg.getInitParameter("encoding");
		if(encoding==null||"".equals(encoding.trim())) {
			encoding = "UTF-8";
		}
	}

}
