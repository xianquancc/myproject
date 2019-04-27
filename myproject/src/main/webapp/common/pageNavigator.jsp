<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div>
	<s:if test="pageResult.totalCount>0">
		<table>
			<tr>
				<td>
					总共<s:property value="pageResult.totalCount"/>条记录，当前<s:property value="pageResult.pageNo"/>页,共<s:property value="pageResult.pageTotalCount"/>
					页
				</td>
			</tr>
		</table>
	</s:if>
	<s:else>没有数据！</s:else>
</div>