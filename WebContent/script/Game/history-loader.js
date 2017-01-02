/**
 * Manage to load history data in game.
 *
 * @class
 * @constructor
 */
var HistoryLoader = function(){};

/**
 * An URL to get history data as JSON.
 *
 * @public
 * @static
 * @var {String}
 */
HistoryLoader.URL_HISTORY_DATA = 'HistoryData';


/**
 * Load history data from server.
 *
 * @public
 * @static
 * @method
 * @var {function} callbackSucceeded - Call back after loading history data.
 */
HistoryLoader.loadAll = function(callbackSucceeded) {
  // console.info('[HistoryLoader.loadAll] IN');

	// - - - - - - - - - - - - - - - -
	// private variables - in HistoryLoader.loadAll

	/**
	 * An instance of HistoryLoader.
	 *
	 * @private
	 * @var {HistoryLoader}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in HistoryLoader.loadAll

	/**
	 * Load field data from a server.
	 *
	 * @private
	 * @method
	 */
	function load() {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callbackLoaded);
		req.open('GET', HistoryLoader.URL_HISTORY_DATA);

		req.send();
	}

	/**
	 * Load field data all from a server.
	 *
	 * @private
	 * @method
	 */
	function loadResult() {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callbackLoaded);
		req.open('GET', HistoryLoader.URL_FIELD_DATA + '?result=1');

		req.send();
	}

	/**
	 * Callback after loading field data from a server.
	 *
	 * @private
	 * @method
	 * @this {EventHandler} A handler for onload event of XMLHttpRequest
	 */
	function callbackLoaded() {
		if (this.status === 200) {
			var result = JSON.parse(this.responseText);
			callbackSucceeded(result);
		}
	}

	// - - - - - - - - - - - - - - - -
	// main - in HistoryLoader.loadAll

	load();

  // console.info('[HistoryLoader.loadAll] OUT');
};
