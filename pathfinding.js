var map = [
	[
		'user',
		'empty',
		'empty',
		'empty',
		'song'
	],
	[
		'wall',
		'wall',
		'wall',
		'wall',
		'empty'
	],
	[
		'empty',
		'empty',
		'empty',
		'empty',
		'empty'
	],
	[
		'empty',
		'wall',
		'wall',
		'wall',
		'wall'
	],
	[
		'empty',
		'empty',
		'empty',
		'empty',
		'monkey'
	]
];

function floodfill(map, x, y, value) {

	var res = map.map(function(arr) {
		return new Array(arr.length);
	});

	var queue = [{x: x, y: y, value: 0}];
	while(queue.length > 0) {
		var n = queue.pop();

		if(map[n.y] === undefined ||
				map[n.y][n.x] === undefined) {
			continue;
		}
		else if(res[n.y] === undefined ||
			res[n.y][n.x] === undefined) {

			if(map[n.y][n.x] !== 'empty' &&
				map[n.y][n.x] !== 'monkey') {
				continue;
			}

			res[n.y][n.x] = {
				type: map[n.y][n.x],
				value: n.value,
				dir: n.dir,
				parent: n.parent
			};
		
			queue.push({x: n.x, y: n.y-1, value: n.value+1, parent: {x: n.x, y: n.y}, dir: 'up'});
			queue.push({x: n.x+1, y: n.y, value: n.value+1, parent: {x: n.x, y: n.y}, dir: 'right'});
			queue.push({x: n.x, y: n.y+1, value: n.value+1, parent: {x: n.x, y: n.y}, dir: 'down'});
			queue.push({x: n.x-1, y: n.y, value: n.value+1, parent: {x: n.x, y: n.y}, dir: 'left'});
		}
	}
	return res;
}

function backtrace(map, x, y) {
	if(map[y][x] === undefined) {
		return [];
	}
	var p = map[y][x].parent;

	if(p === undefined) {
		return [];
	}

	var c = map[p.y][p.x];
	var path = [];
	var last = {x: x, y: y};

	while(c.parent !== undefined) {
		p = c.parent;
		c = map[p.y][p.x];

		path.push(c.dir);
		last = p;
	}
	path.reverse();
	return path;
}

function prettymap(map) {
	var rows = "";
	for(var y = 0; y < map.length; y++) {
		var line = "";
		for(var x = 0; x < map[y].length; x++) {
			switch(map[y][x]) {
				case 'empty': line += '. '; break;
				case 'wall': line += '# '; break;
				case 'song': line += 'S '; break;
				case 'playlist': line += 'P '; break;
				case 'album': line += 'A '; break;
				case 'banana': line += 'B '; break;
				case 'monkey': line += 'M '; break;
				case 'user': line += 'U '; break;
			}
		}
		rows += line + '\n';
	}
	return rows;
}


console.log(prettymap(map));
var res = floodfill(map, 4, 4, 0);
console.log(res);
console.log(backtrace(res, 4, 1));


exports.floodfill = floodfill;
exports.backtrace = backtrace;
exports.prettymap = prettymap;
