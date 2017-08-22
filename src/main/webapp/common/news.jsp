<td colspan="2" bgcolor="#f3f0eb">

<table valign="top" align="left" border="0" cellpadding="0" cellspacing="0" width="225">
<tbody><tr>
<td bgcolor="#f3f0eb">
<img src="/images/spacer.gif" width="10"></td>
<td>
<!-- a href="/news/" --><img src="/images/news.gif" border="0"><!-- /a --></td></tr>
<tr>
	<td colspan="2">
		<div id="feeddiv"></div> <script type="text/javascript">
			var feedcontainer = document.getElementById("feeddiv")
			var feedurl = "http://www.gearwire.com/rss.xml"
			var feedlimit = 8
			var rssoutput = "<!-- br /><b>Latest Music Gear News</b --><ul>"

			function rssfeedsetup() {
				var feedpointer = new google.feeds.Feed(feedurl) //Google Feed API method
				feedpointer.setNumEntries(feedlimit) //Google Feed API method
				feedpointer.load(displayfeed) //Google Feed API method
			}

			function displayfeed(result) {
				if (!result.error) {
					var thefeeds = result.feed.entries
					for (var i = 0; i < thefeeds.length; i++)
						rssoutput += "<li style='margin-bottom:5px'><a href='" + thefeeds[i].link + "'>"
								+ thefeeds[i].title + "</a></li>"
					rssoutput += "</ul>"
					feedcontainer.innerHTML = rssoutput
				} else
					alert("Error fetching feeds!")
			}

			window.onload = function() {
				rssfeedsetup()
			}
		</script>

	</td>

</tr>
</tbody></table>
<!-- ad placement -->
<!-- a href="http://www.howaudio.com" target="OFFSITE"><img src="/images/HA_roundedBox_logo.gif" border="0" width="235"/></a -->

</td>
