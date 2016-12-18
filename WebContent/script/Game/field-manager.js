/**
 * Manage the field data and view.
 *
 * @class
 * @constructor
 * @param {string} fieldId - An ID of a HTML element to attach the field
 * @param {boolean} resultMode - Get field data all when this is true
 */
var FieldManager = function(fieldId, resultMode){
	this.fieldId = fieldId;
	this.resultMode = !!resultMode;
};

/**
 * An URL to get field data string.
 *
 * @public
 * @static
 * @var {String}
 */
FieldManager.URL_FIELD_DATA = 'FieldData';

/**
 * An URL to view the result at failed in game.
 *
 * @public
 * @static
 * @var {String}
 */
FieldManager.URL_GAME_FAILED = 'GameFailed';

/**
 * An URL to view the result at succeeded in game.
 *
 * @public
 * @static
 * @var {String}
 */
FieldManager.URL_GAME_SUCCEEDED = 'GameSucceeded';

/**
 * An ID of a HTML element to attach the field.
 *
 * @public
 * @var {String}
 */
FieldManager.prototype.fieldId = '';

/**
 * Loading field data,
 * the data has all parameters when this value is true,
 * or the data is limited when this value is false.
 *
 * @public
 * @var {String}
 */
FieldManager.prototype.resultMode = false;

/**
 * Load field data and it puts into the view.
 * If you pass an index of a cell,
 * open a cell at the index and then load.
 *
 * @public
 * @method
 * @param  {number} cellIndex - An index of a cell to open
 */
FieldManager.prototype.loadField = function(cellIndex) {
  // console.info('[FieldManager.prototype.loadField] IN');

	// - - - - - - - - - - - - - - - -
	// private variables - in FieldManager.prototype.loadField

	/**
	 * An instance of FieldManager.
	 *
	 * @private
	 * @var {FieldManager}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in FieldManager.prototype.loadField

	/**
	 * Load field data from a server.
	 *
	 * @private
	 * @method
	 */
	function load() {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callback);
		req.open('GET', FieldManager.URL_FIELD_DATA);

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

		req.addEventListener('load', callback);
		req.open('GET', FieldManager.URL_FIELD_DATA + '?result=1');

		req.send();
	}

	/**
	 * Open a cell and load field data from a server.
	 *
	 * @private
	 * @method
	 * @var {number} index - An index of a cell
	 */
	function openCell(index) {
		var req = new XMLHttpRequest();

		req.addEventListener('load', callback);
		req.open('POST', FieldManager.URL_FIELD_DATA);
		req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		// console.info('[FieldManager.prototype.loadField#openCell] index = ' + index);
		req.send('clicked=' + index);
	}

	/**
	 * Callback after loading field data from a server.
	 *
	 * @private
	 * @method
	 * @this {EventHandler} A handler for onload event of XMLHttpRequest
	 */
	function callback() {
		if (this.status === 200) {
			var result = JSON.parse(this.responseText);
			var location;

			if (result.isCompleted) {
				location = FieldManager.URL_GAME_SUCCEEDED;
			}
			else if (0 < result.countSomethingOpened) {
				location = FieldManager.URL_GAME_FAILED;
			}

			if (!self.resultMode && typeof location === 'string') {
				window.location = location;
			}
			else {
				self.updateFieldView(result);
			}

		}
	}

	// - - - - - - - - - - - - - - - -
	// main - in FieldManager.prototype.loadField

	if (self.resultMode) {
		loadResult();
	}
	else if (typeof cellIndex === 'undefined') {
		load();
	}
	else {
		openCell(cellIndex);
	}

  // console.info('[FieldManager.prototype.loadField] OUT');
};

/**
 * Update the field view with field data object.
 *
 * @public
 * @method
 * @param  {object} fieldSource - Parameters to update the field view
 */
FieldManager.prototype.updateFieldView = function(fieldSource) {
  // console.info('[FieldManager.prototype.updateFieldView] IN');

	// - - - - - - - - - - - - - - - -
	// private variables - in FieldManager.prototype.updateFieldView

	/**
	 * Load field data from a server.
	 *
	 * @private
	 * @method
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in FieldManager.prototype.updateFieldView

	/**
	 * Create a field view with field data object.
	 *
	 * @private
	 * @method
	 * @param  {object} source - Parameters to update the field view
	 * @return {HTMLElement} An element as a field view which has cells
	 */
	function createField(source) {
		var domField = document.createElement('section');
		domField.id = self.fieldId;

		// console.log('x: ' + data.width + ", y: " + data.height);

		for (var y = 0; y < source.height; y++) {
			var domRow = document.createElement('div');
			domRow.className = 'row';

			for (var x = 0; x < source.width; x++) {
				var index = y * source.width + x;
				var cell = new Cell(self, source.cells[index]);

				domRow.appendChild(cell.domObject);
			}

			domField.appendChild(domRow);
		}

		return domField;
	}

	/**
	 * Replace a field view to new one.
	 *
	 * @private
	 * @method
	 * @param  {HTMLElement} newField - An element as a field view
	 */
	function replaceField(newField) {
		var oldField = document.querySelector('#' + self.fieldId);
		oldField.parentNode.replaceChild(newField, oldField);
	}

	// - - - - - - - - - - - - - - - -
	// main - in FieldManager.prototype.updateFieldView

	var field = createField(fieldSource);
	replaceField(field);

  // console.info('[FieldManager.prototype.updateFieldView] OUT');
};
