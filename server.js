var request = require('request-json');

var apiKey = 'es+XLozfQucTw1amMoCm0xnSIpo=';
var teamName = 'Music Benders';
var gameId = process.argv[2];
if(!gameId) {
	console.error('Missing game ID!');
	process.exit(1);
}

var url = 'http://competition.monkeymusicchallenge.com/game';

var client = request.newClient(url);

var join = {
	command: 'join game',
	apiKey: apiKey,
	gameId: gameId,
	team: teamName
};

// Join the game!
client.post(url, join, handleResponseLoop);

var ai = require('./ai');

function handleResponseLoop(err, res, resBody) {
	if(err) {
		console.error('Oh noes, error:', err.name, err.message);
		return;
	}
	if(res.statusCode != 200) {
		console.error('Oh noes, the server responded with:', res.statusCode, resBody);
		return;
	}

	var state = resBody;

	if(state.isGameOver) {
		console.log('Game is over, we', state.win ? 'won :D' : 'lost :(');
		return;
	}

	// AI STUFF
	var next = ai.next(state);
	next.apiKey = apiKey;
	next.team = teamName;
	next.gameId = gameId;

	console.log('Sending json:', next);
	// Send the data!
	client.post(url, next, handleResponseLoop);
}
