var http = require('http')
try {
http.get("http://gofabmo.org/manifest/packages.json", function(res) {
	body = ""	
	res.on("data", function(d) { body += d; });
	res.on("end", function() {
		try {
		result = JSON.parse(body);
		packages = result.packages;
		packages = packages.filter(function(package) {
			return (package.os === 'linux') &&
				(package.product === 'FabMo-Updater')
		});
		packages.sort(function(a,b) {
			return b.version.localeCompare(a.version);
		});		
		console.log(packages[0].url);
		process.exit(0);
		} catch(e) {
			console.log(e);
			process.exit(1);
		}
	});
	
}); 
} catch(e) {
	console.log(e);
	process.exit(1);
}
