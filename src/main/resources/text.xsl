<?xml version="1.0" encoding="US-ASCII"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<body style="font-family:Calibri;font-size:13pt;background-color:#fff">
		
<!-- 		<div style="width: 1190px; display: block; margin-left: auto; margin-right: auto;"> -->
		<div style="display: block">
		
			<xsl:for-each select="noticias/noticia">
<!-- 				<div style="float: left; width:550px; max-width:550px; background-color:#ddd; padding:10px; margin:10px"> -->
				<div style="width:550px; max-width:550px; background-color:#ddd; padding:10px; margin:10px; margin-left: auto; margin-right: auto;">
			
					<a style="text-decoration: none; color:#333" target="_blank">
						<xsl:attribute name="href">
							<xsl:value-of select="urlPagina"/>
						</xsl:attribute>
					
						<!-- Headline -->
						<div style="color:#555">
							<p><xsl:value-of select="titulo" /></p>
						</div>
						
						<!-- Image -->
						<div style="width: 100%; margin: 0 auto;">
							<div style="display: table;margin: 0 auto;">
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="imagem" />
									</xsl:attribute>
								</img>
							</div>
						</div>
						
						<!-- Author - Time -->
						<div style="font-size: 9pt; color:#1e88e5">
							<p><xsl:value-of select="autor" /><br/><xsl:value-of select="data" /></p>
						</div>
						
						<!-- Descricao -->
						<div style="font-size: 11pt;text-align:justify;color:#333">
							<p><xsl:value-of select="descricao" /></p>							
						</div>

						<!-- Corpo -->
						<div style="font-size: 11pt;text-align:justify;color:#333">
							<p><xsl:value-of select="corpo" /></p>							
						</div>
						
						<!-- Category -->
						<div style="font-size: 8pt; color:#888">	
							<p>Category - <xsl:value-of select="categoria" /></p>
						</div>
						
					</a>
				
				</div>

			</xsl:for-each>
			
		</div>
		
	</body>
	
</html>