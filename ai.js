var pathfinding = require('./pathfinding');

function next(state) {

	var map = state.layout;
	pathfinding.prettymap(map);
	pathfinding.floodfill(map, state.position[1], state.position[0], 0);
	console.log(map);
	console.log(pathfinding.backtrace(map, 0, 0));
	process.exit(1);

	var json = {
		command: 'move',
		direction: 'up'
	};
	json = idle(state);
	return json;
}


function idle(state) {
	var json = {
		command: 'idle'
	};

	return json;
}

exports.next = next;
