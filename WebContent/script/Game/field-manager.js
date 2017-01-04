/**
 * Manage the field data and view.
 *
 * @class
 * @constructor
 * @param {string} fieldId - An ID of a HTML element to attach the field
 * @param {boolean} isResultMode - Get field data all when this is true
 */
var FieldManager = function(fieldId, isResultMode){
	this.isResultMode = !!isResultMode;
	this.fieldObject = new FieldObject(this, fieldId);
};

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
 * Loading field data,
 * the data has all parameters when this value is true,
 * or the data is limited when this value is false.
 *
 * @public
 * @var {String}
 */
FieldManager.prototype.isResultMode = false;

/**
 * An object which wraps a HTML element as a field.
 *
 * @public
 * @var {FieldObject}
 */
FieldManager.prototype.fieldObject = undefined;


/**
 * Update the field view with field data object.
 *
 * @public
 * @method
 * @param  {object} cellDataArray - Cell data to update cells in the field view
 * @see FieldObject#updateViewByCellDataArray
 */
FieldManager.prototype.updateViewByCellDataArray = function(cellDataArray) {
	this.fieldObject.updateViewByCellDataArray(cellDataArray);
};

/**
 * Load field data and it puts into the view.
 * If you pass an index of a cell,
 * open a cell at the index and then load.
 *
 * @public
 * @method
 * @param  {number} cellIndex - An index of a cell to open
 * @see FieldObject#loadField
 */
FieldManager.prototype.loadField = function() {
	this.fieldObject.loadField();
}

/**
 * Callback called after loading a field data.
 *
 * @public
 * @method
 * @param  {object} fieldData - The source of a HTML element as a field
 */
FieldManager.prototype.callbackLoadedField = function(fieldData) {
	var isGameEnd = false;
	var location;

	if (fieldData.isCompleted) {
		location = FieldManager.URL_GAME_SUCCEEDED;
	}
	else if (0 < fieldData.countSomethingOpened) {
		location = FieldManager.URL_GAME_FAILED;
	}

	if (!this.isResultMode && typeof location === 'string') {
		window.location = location;
	}

	return isGameEnd;	
}


/**
 * Update the field clear.
 *
 * @public
 * @method
 * @see FieldObject#resetView
 */
FieldManager.prototype.resetView = function() {
	this.fieldObject.resetView();
};

/**
 * Update a field with data for it.
 *
 * @public
 * @method
 * @param  {object} fieldData - The source of a HTML element as a field
 * @see FieldObject#updateField
 */
FieldManager.prototype.updateField = function(fieldData) {
	this.fieldObject.updateField(fieldData);
};

/**
 * Replace a cell element in the field view.
 *
 * @public
 * @method
 * @param  {HTMLElement} newCell - A new cell element as a replacement
 * @param  {HTMLElement} oldCell - An old cell element as a replacement
 */
FieldManager.prototype.replaceCell = function(newCell, oldCell){
	this.fieldObject.replaceCell(newCell, oldCell);
}

