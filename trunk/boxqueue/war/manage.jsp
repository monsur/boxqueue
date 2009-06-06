<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<html>
<head>
  <title>Boxqueue</title>
  <link rel="stylesheet" href="/static/css/main.css" type="text/css" />
  <link type="text/css" href="/static/lib/jquery-ui-1.7.2.custom/css/start/jquery-ui-1.7.2.custom.css" rel="Stylesheet" /> 
  <script type="text/javascript" src="/static/lib/jquery-ui-1.7.2.custom/js/jquery-1.3.2.min.js"></script>
  <script type="text/javascript" src="/static/lib/jquery-ui-1.7.2.custom/js/jquery-ui-1.7.2.custom.min.js"></script>
  <script type="text/javascript">
    $(function() {
      $("#feedaccordion").accordion({ header: "h3" });
      $("#menuaccordion").accordion({ header: "h3" });
    });
  </script>
</head>
<body>

  <div id="header">
    <div id="headersite">
      <div id="headersitename">
        <span class="green">Box</span><span class="blue">queue</span>
      </div>
      <div id="headersitetagline">
        Create your own channel.
      </div>
    </div>
    <div id="headeruser">
    logged in as: <%= StringEscapeUtils.escapeHtml(request.getAttribute("username").toString()) %>
    | <a href="<%= StringEscapeUtils.escapeHtml(request.getAttribute("logoutUrl").toString()) %>">logout</a>
    </div>
  </div>

<div id="content">  
  <div id="feedaccordion">
    <div>
      <h3><a href="#">Your Boxqueue Feed</a></h3>
      <div>
          <div id="feedurl">
          <%= StringEscapeUtils.escapeHtml(request.getAttribute("feedurl").toString()) %>
          </div>
       </div>
     </div>
  </div><br />

    <div id="menuaccordion">
      <div>
        <h3><a href="#">Boxqueue QuickStart</a></h3>
        <div>
          Boxqueue lets you save videos from across the web, and then watch them later in Boxee! In order to start using Boxqueue, follow these steps:
          <ol>
            <li>Add this link to the
            <a href="http://app.boxee.tv/home/rss" target="_blank">RSS section</a> of your Boxee
            account:<br /><br /> <%= StringEscapeUtils.escapeHtml(request.getAttribute("feedurl").toString()) %>
            </li><br /><br />
            <li>Drag this bookmarklet to your browser's bookmarks bar:
            <a href="<%= request.getAttribute("bookmarklet").toString() %>">Add to Boxqueue</a></li><br /><br />
            <li>Click that bookmarklet whenever you wish to save a video to watch later.</li><br /><br />
            <li>Visit the My Feeds section in Boxee to watch your saved videos.</li><br />
          </ol>
          If you'd like more information about how to use Boxqueue, read the "Detailed Instructions" section below.
        </div>
      </div>
      <div>
        <h3><a href="#">Detailed Instructions</a></h3>
        <div>
          <p>Boxqueue lets you save videos from across the web, and then watch them later in
          <a href="http://boxee.tv">Boxee</a>! In order to start using Boxqueue, follow these steps:</p>
          
          <p><b>Add your Boxqueue feed to Boxee:</b>
          <ol>
            <li>Copy the url to your Boxqueue feed from above.</li>
            <li>Visit the RSS section of your Boxee account:
            <a href="http://app.boxee.tv/home/rss">http://app.boxee.tv/home/rss</a></li>
            <li>Paste your Boxqueue feed into the box labeled "Feed address" and click "Add rss".
            Your Boxqueue feed is now ready to be viewed from Boxee!</li>
          </ol></p>
          
          <p><b>Add videos to your Boxqueue feed:</b>
          <ol>
            <li>Drag the following link into your browser's bookmarks bar:
            <a href="<%= request.getAttribute("bookmarklet").toString() %>">Add to Boxqueue</a></li>
            <li>When you find a video on a site you'd like to watch later, click that link and it
            will be added to your feed.</li>
          </ol></p>
          
          <p><b>Watch your Boxqueue feed:</b>
          <ol>
            <li>When you're ready to watch the videos in your feed, run Boxee and navigate to the 
            following section: Video %gt; Internet %gt; My Feeds</li>
            <li>Select your Boxqueue feed from the list.  This will show you the videos you've
            queued up.  Once you watch a video, it will be removed from your queue.</li>
          </ol></p>
        </div>
      </div>
      <div>
        <h3><a href="#">Feedback</a></h3>
        <div>If you have any questions or comments about Boxqueue, plese send them here:
        <a href="mailto:monsur+boxqueue@gmail.com">monsur+boxqueue@gmail.com</a></div>
      </div>
    </div>
</div>

</body>
</html>