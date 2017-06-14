/**
 * Gets an XPath for an node which describes its hierarchical location.
 */
var getNodeXPath = function(node) {
    if (node && node.id)
        return '//*[@id="' + node.id + '"]';
    else
        return getNodeTreeXPath(node);
};

var getNodeTreeXPath = function(node) {
    var paths = [];

    // Use nodeName (instead of localName) so namespace prefix is included (if any).
    for (; node && (node.nodeType == 1 || node.nodeType == 3) ; node = node.parentNode)  {
        var index = 0;
        // EXTRA TEST FOR ELEMENT.ID
        if (node && node.id) {
            paths.splice(0, 0, '/*[@id="' + node.id + '"]');
            break;
        }

        for (var sibling = node.previousSibling; sibling; sibling = sibling.previousSibling) {
            // Ignore document type declaration.
            if (sibling.nodeType == Node.DOCUMENT_TYPE_NODE)
                continue;

            if (sibling.nodeName == node.nodeName)
                ++index;
        }

        var tagName = (node.nodeType == 1 ? node.nodeName.toLowerCase() : "text()");
        var pathIndex = (index ? "[" + (index+1) + "]" : "");
        paths.splice(0, 0, tagName + pathIndex);
    }

    return paths.length ? "/" + paths.join("/") : null;
};

links = []
fields = []
asserts = []
lastField = null;

$("body").dblclick(function(evt) {
    var field = prompt();
    links.push("sp." + field + ".type=xpath")
    links.push("sp." + field + ".value" +  "=" + getNodeXPath(evt.target))
    lastField  = field;
    fields.push("private String " + field + ";")
    lastField = lastField.replace(/\b\w/g, function(l){ return l.toUpperCase() })
    asserts.push("assertEquals(\"" + evt.target.innerHTML + "\", nfe.get" + lastField  + "());")
})

function printAll() {
  for(var i in links) {
    console.log(links[i]);
  }
}

function printAllAsserts() {
  for(var i in asserts) {
    console.log(asserts[i]);
  }
}

function printAllFields() {
  for(var i in fields) {
    console.log(fields[i]);
  }
}
