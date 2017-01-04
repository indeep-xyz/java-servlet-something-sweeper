/**
 * Manage to load the field data from server.
 *
 * @class
 * @constructor
 */
var FieldLoader = function(){
};

/**
 * An URL to get field data string.
 *
 * @public
 * @static
 * @var {String}
 */
FieldLoader.URL_FIELD_DATA = 'FieldData';

/**
 * Load field data from the server which has
 * the state of a field.
 *
 * @public
 * @method
 * @param {boolean} isResultMode - Get field data all when this is true
 * @param  {function} callbackLoadedField - Callback called after loading field data
 */
FieldLoader.prototype.load = function(isResultMode, callbackLoadedField) {
	// - - - - - - - - - - - - - - - -
	// private variables - in FieldLoader.prototype.loadField

	/**
	 * An instance of FieldLoader.
	 *
	 * @private
	 * @var {FieldLoader}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in FieldLoader.prototype.loadField

	/**
	 * Load field data from server.
	 * The data is limited by the state of current game.
	 *
	 * @private
	 * @method
	 */
	function loadCurrentState() {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callback);
		req.open('GET', FieldLoader.URL_FIELD_DATA);

		req.send();
	}

	/**
	 * Load field data all from server.
	 *
	 * @private
	 * @method
	 */
	function loadAll() {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callback);
		req.open('GET', FieldLoader.URL_FIELD_DATA + '?result=1');

		req.send();
	}

	/**
	 * Callback after loading field data from server.
	 *
	 * @private
	 * @method
	 * @this {EventHandler} A handler for onload event of XMLHttpRequest
	 */
	function callback() {
		if (this.status === 200) {
			var fieldData = JSON.parse(this.responseText);
			callbackLoadedField(fieldData);
		}
	}

	// - - - - - - - - - - - - - - - -
	// main - in FieldLoader.prototype.loadField

	if (isResultMode) {
		loadAll();
	}
	else {
		loadCurrentState();
	}
};

/**
 * Open a cell and load a new field data from the server
 * which has the state of a field.
 *
 * @public
 * @method
 * @param  {number} cellIndex - An index of a cell to open
 * @param  {function} callbackLoadedField - Callback called after loading field data
 */
FieldLoader.prototype.openCell = function(cellIndex, callbackLoadedField) {
	// - - - - - - - - - - - - - - - -
	// private variables - in FieldLoader.prototype.openCell

	/**
	 * An instance of FieldLoader.
	 *
	 * @private
	 * @var {FieldLoader}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in FieldLoader.prototype.openCell

	/**
	 * Open a cell and load field data from server.
	 *
	 * @private
	 * @method
	 * @var {number} index - An index of a cell
	 */
	function open(index) {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callback);
		req.open('POST', FieldLoader.URL_FIELD_DATA);
		req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		// console.info('[FieldLoader.prototype.openCell#openCell] index = ' + index);
		req.send('clicked=' + index);
	}

	/**
	 * Callback after loading field data from server.
	 *
	 * @private
	 * @method
	 * @this {EventHandler} A handler for onload event of XMLHttpRequest
	 */
	function callback() {
		if (this.status === 200) {
			var fieldData = JSON.parse(this.responseText);
			callbackLoadedField(fieldData, self.resultMode);
		}
	}

	// - - - - - - - - - - - - - - - -
	// main - in FieldLoader.prototype.openCell

	open(cellIndex);
};
