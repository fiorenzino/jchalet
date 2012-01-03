<html>
<HEAD>
<TITLE>JBossAS7 JSP snoop page</TITLE>
<%@ page import="javax.servlet.http.HttpUtils,java.util.Enumeration"%>
<%@ page import="java.lang.management.*"%>
<%@ page import="java.util.*"%>
</HEAD>
<body>

	<H1>WebApp JSP Snoop page</H1>
	<img src="images/add.png">

	<h2>JVM Memory Monitor</h2>


	<table style="border: 0px; width: 100%;">

		<tbody>
			<tr>
				<td colspan="2" align="center">
					<h3>Memory MXBean</h3>
				</td>
			</tr>

			<tr>
				<td width="200">Heap Memory Usage</td>
				<td><%=ManagementFactory.getMemoryMXBean().getHeapMemoryUsage()%>
				</td>
			</tr>

			<tr>
				<td>Non-Heap Memory Usage</td>
				<td><%=ManagementFactory.getMemoryMXBean()
					.getNonHeapMemoryUsage()%></td>
			</tr>

			<tr>
				<td colspan="2"></td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<h3>Memory Pool MXBeans</h3>
				</td>
			</tr>

		</tbody>
	</table>
	<%
		Iterator iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
		while (iter.hasNext()) {
			MemoryPoolMXBean item = (MemoryPoolMXBean) iter.next();
	%>

	<table style="border: 1px #98AAB1 solid; width: 100%">

		<tbody>
			<tr>
				<td colspan="2" align="center"><strong><%=item.getName()%></strong></td>
			</tr>

			<tr>
				<td width="200">Type</td>
				<td><%=item.getType()%></td>
			</tr>

			<tr>
				<td>Usage</td>
				<td><%=item.getUsage()%></td>
			</tr>

			<tr>
				<td>Peak Usage</td>
				<td><%=item.getPeakUsage()%></td>
			</tr>

			<tr>
				<td>Collection Usage</td>
				<td><%=item.getCollectionUsage()%></td>
			</tr>

		</tbody>
	</table>


	<%
		}
	%>

	<h2>Request information</h2>

	<table>
		<tr>
			<TH align=right>Requested URL:</TH>
			<td><%=HttpUtils.getRequestURL(request)%></td>
		</tr>
		<tr>
			<TH align=right>Request method:</TH>
			<td><%=request.getMethod()%></td>
		</tr>
		<tr>
			<TH align=right>Request URI:</TH>
			<td><%=request.getRequestURI()%></td>
		</tr>
		<tr>
			<TH align=right>Request protocol:</TH>
			<td><%=request.getProtocol()%></td>
		</tr>
		<tr>
			<TH align=right>Servlet path:</TH>
			<td><%=request.getServletPath()%></td>
		</tr>
		<tr>
			<TH align=right>Path info:</TH>
			<td><%=request.getPathInfo()%></td>
		</tr>
		<tr>
			<TH align=right>Path translated:</TH>
			<td><%=request.getPathTranslated()%></td>
		</tr>
		<tr>
			<TH align=right>Query string:</TH>
			<td>
				<%
					if (request.getQueryString() != null)
						out.write(request.getQueryString().replaceAll("<", "&lt;")
								.replaceAll(">", "&gt;"));
				%>
			</td>
		</tr>
		<tr>
			<TH align=right>Content length:</TH>
			<td><%=request.getContentLength()%></td>
		</tr>
		<tr>
			<TH align=right>Content type:</TH>
			<td><%=request.getContentType()%></td>
		<tr>
		<tr>
			<TH align=right>Server name:</TH>
			<td><%=request.getServerName()%></td>
		<tr>
			<T H align=right>Server port:
			</TH>
			<td><%=request.getServerPort()%></td>

			<tr>
			<tr>
				<TH align=right>Remote user:</TH>
				<td><%=request.getRemoteUser()%></td>
			<tr>
			<tr>
				<TH align=right>Remote address:</TH>
				<td><%=request.getRemoteAddr()%></td>
			<tr>
			<tr>
				<TH align=right>Remote host:</TH>
				<td><%=request.getRemoteHost()%></td>
			<tr>
			<tr>
				<TH align=right>Authorization scheme:</TH>
				<td><%=request.getAuthType()%></td>
			<tr>
	</table>

	<%
		Enumeration<String> e = request.getHeaderNames();
		if (e != null && e.hasMoreElements()) {
	%>
	<h2>Request headers</h2>

	<table>
		<tr>
			<TH align=left>Header:</TH>
			<TH align=left>Value:</TH>
		</tr>
		<%
			while (e.hasMoreElements()) {
					String k = (String) e.nextElement();
		%>
		<tr>
			<td><%=k%></td>
			<td><%=request.getHeader(k)%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>


	<%
		e = request.getParameterNames();
		if (e != null && e.hasMoreElements()) {
	%>
	<h2>Request parameters</h2>
	<table>
		<TR valign=top>
			<TH align=left>Parameter:</TH>
			<TH align=left>Value:</TH>
			<TH align=left>Multiple values:</TH>
		</tr>
		<%
			while (e.hasMoreElements()) {
					String k = (String) e.nextElement();
					String val = request.getParameter(k);
					String vals[] = request.getParameterValues(k);
		%>
		<TR valign=top>
			<td><%=k.replaceAll("<", "&lt;").replaceAll(">", "&gt;")%></td>
			<td><%=val.replaceAll("<", "&lt;").replaceAll(">",
							"&gt;")%></td>
			<td>
				<%
					for (int i = 0; i < vals.length; i++) {
								if (i > 0)
									out.print("<BR>");
								out.print(vals[i].replaceAll("<", "&lt;").replaceAll(
										">", "&gt;"));
							}
				%>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>


	<%
		e = request.getAttributeNames();
		if (e != null && e.hasMoreElements()) {
	%>
	<h2>Request Attributes</h2>
	<table>
		<TR valign=top>
			<TH align=left>Attribute:</TH>
			<TH align=left>Value:</TH>
		</tr>
		<%
			while (e.hasMoreElements()) {
					String k = (String) e.nextElement();
					Object val = request.getAttribute(k);
		%>
		<TR valign=top>
			<td><%=k.replaceAll("<", "&lt;").replaceAll(">", "&gt;")%></td>
			<td><%=val.toString().replaceAll("<", "&lt;")
							.replaceAll(">", "&gt;")%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>


	<%
		e = getServletConfig().getInitParameterNames();
		if (e != null && e.hasMoreElements()) {
	%>
	<h2>Init parameters</h2>
	<table>
		<tr valign=top>
			<th align=left>Parameter:</th>
			<th align=left>Value:</th>
		</tr>
		<%
			while (e.hasMoreElements()) {
					String k = (String) e.nextElement();
					String val = getServletConfig().getInitParameter(k);
		%>
		<tr valign=top>
			<td><%=k%></td>
			<td><%=val%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>

</body>
</html>