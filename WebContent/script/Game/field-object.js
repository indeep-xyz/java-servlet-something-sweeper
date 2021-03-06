/**
 * Manage field data and its view in HTML.
 *
 * @class
 * @constructor
 * @param {FieldManager} fieldManager - An instance of FieldManager
 * @param {string} fieldId - An ID of a HTML element to attach the field
 */
var FieldObject = function(fieldManager, fieldId){
	this.fieldManager = fieldManager;
	this.fieldLoader = new FieldLoader();
	this.setFieldById(fieldId);
};

/**
 * A HTML element as a field.
 *
 * @public
 * @type {HTMLElement}
 */
FieldObject.prototype.field = null;

/**
 * An object which loads field data from server.
 *
 * @public
 * @type {FieldLoader}
 */
FieldObject.prototype.fieldLoader = undefined;

/**
 * Get an ID attribute of a HTML element as a field.
 *
 * @public
 * @method
 * @return  {string} Return an ID attribute of a HTML element of a field when an instance has the element, else return a blank string
 */
FieldObject.prototype.getFieldId = function() {
	var id = "";
	
	if (this.field !== null) {
		id = this.field.getAttribute('id');
	}

	return id;
}

/**
 * Set a HTML element as a field.
 *
 * @public
 * @method
 * @param  {HTMLElement} newField - A new field element as a replacement
 */
FieldObject.prototype.setField = function(field) {
	if (this.field !== null) {
		var oldField = document.getElementById(this.getFieldId());
		oldField.parentNode.replaceChild(field, oldField);
	}

	this.field = field;
}

/**
 * Set a HTML element as a field by an ID attribute.
 *
 * @public
 * @method
 * @param {string} fieldId - An ID of a HTML element to attach the field
 */
FieldObject.prototype.setFieldById = function(fieldId) {
	var field = document.getElementById(fieldId);
	this.setField(field);
}

/**
 * Update the field view with field data object.
 *
 * @public
 * @method
 * @param  {object} fieldData - The source of a HTML element as a field
 */
FieldObject.prototype.updateField = function(fieldData) {
	// - - - - - - - - - - - - - - - -
	// private variables - in FieldObject.prototype.updateField

	/**
	 * Load field data from a server.
	 *
	 * @private
	 * @type {FieldObject}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in FieldObject.prototype.updateField

	/**
	 * Create a HTML element as a field.
	 *
	 * @private
	 * @method
	 * @param  {array} cells - The source data of cells
	 * @param  {number} width - Width of a field
	 * @param  {number} height - Height of a field
	 * @return {HTMLElement} A HTML element as a field
	 */
	function createField(cells, width, height) {
		var field = document.createElement('section');
		var fieldId = self.field.getAttribute('id');
		field.setAttribute('id', fieldId);

		// console.log('x: ' + width + ", y: " + height);

		for (var y = 0; y < height; y++) {
			var row = createFieldRow(cells, width, y);
			field.appendChild(row);
		}

		return field;
	}

	/**
	 * Create a HTML element as a row of a field.
	 *
	 * @private
	 * @method
	 * @param  {array} cells - The source data of cells
	 * @param  {number} width - Width of a field
	 * @param  {number} y - Y-coordinate of the field
	 * @return {HTMLElement} a HTML element as a row of a field
	 */
	function createFieldRow(cells, width, y) {
		var row = document.createElement('div');
		row.className = 'row';

		for (var x = 0; x < width; x++) {
			var index = y * width + x;
			var cell = new CellObject(self, cells[index], true);

			row.appendChild(cell.domObject);
		}

		return row;
	}

	// - - - - - - - - - - - - - - - -
	// main - in FieldObject.prototype.updateFieldView

	var field = createField(fieldData.cells, fieldData.width, fieldData.height);
	this.setField(field);

  // console.info('[FieldObject.prototype.updateFieldView] OUT');
};

/**
 * Update the field with field data.
 *
 * @public
 * @method
 * @param  {object} cellDataArray - Cell data to update cells in the field view
 */
FieldObject.prototype.updateViewByCellDataArray = function(cellDataArray) {
	var tmpField = this.field.cloneNode(true);
	var tmpCellArray = tmpField.querySelectorAll('.cell');

	for (var i = 0; i < cellDataArray.length; i++) {
		var newCellObject = new CellObject(this, cellDataArray[i]);
		var newCell = newCellObject.domObject;
		var oldCell = tmpCellArray[newCellObject.index];
		
		if (newCell.className !== oldCell.className) {
			this.replaceCell(newCellObject.domObject, oldCell);
		}
	}

	this.setField(tmpField);
};

/**
 * Load field data and then put it into the field element.
 *
 * @public
 * @method
 */
FieldObject.prototype.loadField = function() {
	var self = this;
	
	this.fieldLoader.load(this.fieldManager.isResultMode, function(fieldData) {
		var isGameEnd = self.fieldManager.callbackLoadedField(fieldData);
		
		if (!isGameEnd) {
			self.updateField(fieldData);
		}
	});
}

/**
 * Load field data from the cache data.
 *
 * @public
 * @method
 * @return {boolean} The value is true when loading from cache of an instance, else false
 * @see FieldLoader#loadFromCache
 */
FieldObject.prototype.loadFieldFromCache = function() {
	var self = this;
	
	return this.fieldLoader.loadFromCache(function(fieldData) {
		var isGameEnd = self.fieldManager.callbackLoadedField(fieldData);
		
		if (!isGameEnd) {
			self.updateField(fieldData);
		}
	});
}

/**
 * Open a cell and load field data and then
 * put it into the field element.
 *
 * @public
 * @method
 * @param  {number} cellIndex - An index of a cell to open
 */
FieldObject.prototype.openCell = function(cellIndex) {
	var self = this;
	
	this.fieldLoader.openCell(cellIndex, function(fieldData) {
		var isGameEnd = self.fieldManager.callbackLoadedField(fieldData);
		
		if (!isGameEnd) {
			self.updateField(fieldData);
		}
	});
}

/**
 * Clear the field view.
 *
 * @public
 * @method
 */
FieldObject.prototype.clearField = function() {
	var tmpField = this.field.cloneNode(true);
	var tmpCellArray = tmpField.querySelectorAll('.cell');
	
	for (var i = 0; i < tmpCellArray.length; i++) {
		var newCellObject = new CellObject(null, {index: i});
		var newCell = newCellObject.domObject;
		var oldCell = tmpCellArray[i];

		if (newCell.className !== oldCell.className) {
			this.replaceCell(newCell, oldCell);
		}
	}

	this.setField(tmpField);
};

/**
 * Replace a cell element in the field view.
 *
 * @public
 * @method
 * @param  {HTMLElement} newCell - A new cell element as a replacement
 * @param  {HTMLElement} oldCell - An old cell element as a replacement
 */
FieldObject.prototype.replaceCell = function(newCell, oldCell){
	oldCell.parentNode.replaceChild(newCell, oldCell);
	oldCell = null;
}

