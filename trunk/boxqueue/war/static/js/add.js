function toggleItem(id){
  var item = document.getElementById(id);
  if(item){
    if ( item.style.display == 'none'){
      item.style.display = '';
    }
    else{
      item.style.display = 'none';
    } 
  }
}

function showItem(id){
  try{
    var item = document.getElementById(id);
    if(item){
        item.style.display = '';
    }
  }
  catch(e){
  
  }
}

(function(){
  var url;
  try {
    url = window.location.href;
  } catch(e) {
    url = '';
  }
  var iframe_url = 'http://localhost:8080/private/add?url='+ encodeURIComponent(url);
  var existing_iframe = document.getElementById('boxqueue_add_bookmarklet_iframe');
  if (existing_iframe){
    showItem('boxqueue_add_bookmarklet');
    existing_iframe.src = iframe_url;
    return;
  }
  var div = document.createElement('div');
  div.id = 'boxqueue_add_bookmarklet';
  var str = '';
  str += '<table id="boxqueue_add_bookmarklet_table" valign="top" width="570" cellspacing="0" cellpadding="0"><tr><td width ="550" height="80">';
  str += '<iframe frameborder="0" scrolling="no" name="boxqueue_add_bookmarklet_iframe" id="boxqueue_add_bookmarklet_iframe" src="' + iframe_url + '" width="550px" height="75px" style="textalign:right; backgroundColor: white;"></iframe>';
  str += '</td><td onClick="toggleItem(\'boxqueue_add_bookmarklet\');" style="background: #FFDDDD;" title="click to close window" valign="top" align="center" width="20px">';
  str += '<a href="javascript:void(0);" style="width:100%; text-align: middle; color: #FF0000; font-family: Arial;">x</a>';
  str += '</td></tr></table>';
  div.innerHTML = str;
  document.body.insertBefore(div, document.body.firstChild);
})();

