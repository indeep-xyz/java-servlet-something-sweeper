/**
 * Manage field data.
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
 * An URL to display the result of a failed game.
 *
 * @public
 * @static
 * @type {String}
 */
FieldManager.URL_GAME_FAILED = 'GameFailed';

/**
 * An URL to display the result of a succeeded game.
 *
 * @public
 * @static
 * @type {String}
 */
FieldManager.URL_GAME_SUCCEEDED = 'GameSucceeded';

/**
 * The mode of result or not.
 *
 * @public
 * @type {String}
 */
FieldManager.prototype.isResultMode = false;

/**
 * An object which wraps a HTML element as a field.
 *
 * @public
 * @type {FieldObject}
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
 *
 * @public
 * @method
 * @see FieldObject#loadField
 */
FieldManager.prototype.loadField = function() {
	this.fieldObject.loadField();
}

/**
 * Load field data from the cache data.
 *
 * @public
 * @method
 * @see FieldObject#loadFieldFromCache
 * @see FieldLoader#loadFromCache
 */
FieldManager.prototype.loadFieldFromCache = function() {
	return this.fieldObject.loadFieldFromCache();
}

/**
 * Callback called after loading a field data.
 *
 * @public
 * @method
 * @param  {object} fieldData - The source of a HTML element as a field
 * @return {boolean} Return true when the current game ends in the passed field data, else false
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
 * Clear the field view.
 *
 * @public
 * @method
 * @see FieldObject#clearField
 */
FieldManager.prototype.clearField = function() {
	this.fieldObject.clearField();
};

/**
 * Update the field view with field data object.
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

