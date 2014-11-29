var request = require('request-json');

var apiKey = 'es+XLozfQucTw1amMoCm0xnSIpo=';
var teamName = 'Music Benders';
var gameId = process.argv[2];

var url = 'http://competition.monkeymusicchallenge.com/game';

var client = request.newClient(url);

var json = {
	command: 'join game',
	apiKey: apiKey,
	gameId: gameId,
	team: teamName
}

client.post(url, json, handleResponseLoop);


function handleResponseLoop(err, res, resBody) {
	if(err) {
		console.error('Oh noes, error:', err.name, err.message);
		process.exit(1);
	}
	if(res.statusCode != 200) {
		console.error('Oh noes, the server responded with:', res.statusCode);
		process.exit(1);
	}

	console.log(resBody);
};
