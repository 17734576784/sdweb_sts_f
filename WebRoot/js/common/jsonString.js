var jsonString = {
	json2String : function(json) {
		var THIS = this;
		switch (typeof (json)) {
		case 'string':
			return '"' + json.replace(/(["\\])/g, '\\$1') + '"';
		case 'array':
			return '[' + json.map(THIS.json2String).join(',') + ']';
		case 'object':
			if (json instanceof Array) {
				var strArr = [];
				var len = json.length;
				for ( var i = 0; i < len; i++) {
					strArr.push(THIS.json2String(json[i]));
				}
				return '[' + strArr.join(',') + ']';
			} else if (json == null) {
				return 'null';

			} else {
				var string = [];
				for ( var property in json)
					string.push(THIS.json2String(property) + ':' + THIS.json2String(json[property]));
				return '{' + string.join(',') + '}';
			}
		case 'number':
			return json;
		case false:
			return json;
		}
	},
	string2Json : function(json_str) {
		return eval('(' + json_str + ')');
	}
};