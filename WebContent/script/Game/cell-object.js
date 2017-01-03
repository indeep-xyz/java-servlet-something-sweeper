/**
 * A cell.
 *
 * @class
 * @constructor
 * @param {FieldManager} fieldManager - An instance to manage field
 * @param {object} source - Parameters to create a cell
 * @param {boolean} doesAttachEvent - Set a click event when it is true
 */
var CellObject = function(fieldManager, source, doesAttachEvent){
	this.fieldManager = fieldManager;
	this.index = source.index;
	this.isOpen = source.isOpen;
	
	this.initDomObject(source, doesAttachEvent);
};

/**
 * An instance to manage field.
 *
 * @public
 * @var {FieldManager}
 */
CellObject.prototype.fieldManager = undefined;

/**
 * Parameters to create a cell.
 *
 * @public
 * @var {object}
 */
CellObject.prototype.source = undefined;

/**
 * An ID of the cell.
 *
 * @public
 * @var {number}
 */
CellObject.prototype.index = undefined;

/**
 * A status that the cell is open or not.
 *
 * @public
 * @var {boolean}
 */
CellObject.prototype.isOpen = undefined;

/**
 * A DOM object as a cell.
 *
 * @public
 * @var {HTMLElement}
 */
CellObject.prototype.domObject = undefined;

/**
 * Initialize a DOM object in an instance.
 *
 * @public
 * @method
 * @param  {object} source - Parameters to create a cell
 * @param  {boolean} doesAttachEvent - Set a click event when it is true
 */
CellObject.prototype.initDomObject = function(source, doesAttachEvent) {
	// - - - - - - - - - - - - - - - -
	// private variables - in CellObject.prototype.initDomObject

	/**
	 * An instance of FieldManager.
	 *
	 * @private
	 * @var {FieldManager}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in CellObject.prototype.initDomObject

	/**
	 * Create a cell initialized common settings.
	 *
	 * @private
	 * @method
	 * @return {HTMLElement} A cell initialized common settings
	 */
	function createBaseCell() {
		var cell = document.createElement('div');
		cell.className = 'cell';

		return cell;
	}

	/**
	 * Create a plain cell.
	 *
	 * @private
	 * @method
	 * @param  {number} aroundSomething - The number of something cells around a cell
	 * @return {HTMLElement} A plain cell
	 */
	function createPlainCell(aroundSomething) {
		var cell = createBaseCell();
		cell.className += ' opened plain';

		if (0 < aroundSomething) {
			cell.innerHTML = aroundSomething;
		}

		return cell;
	}

	/**
	 * Create a something cell.
	 *
	 * @private
	 * @method
	 * @return {HTMLElement} A something cell
	 */
	function createSomethingCell() {
		var cell = createBaseCell();
		cell.className += ' opened something';

		return cell;
	}

	/**
	 * Create a cell which does not open yet.
	 *
	 * @private
	 * @method
	 * @param  {number} index - An ID of a cell
	 * @param  {boolean} doesAttachEvent - Set a click event when it is true
	 * @return {HTMLElement} An unknown cell
	 */
	function createUnknownCell(index, doesAttachEvent) {
		var cell = createBaseCell();

		if (doesAttachEvent) {
			cell.className += ' clickable';

			cell.addEventListener('click', function() {
				self.fieldManager.loadField(index);
			});
		}

		return cell;
	}

	// - - - - - - - - - - - - - - - -
	// main - in CellObject.prototype.initDomObject

	// domObject.innerHTML = source.html;

	var domObject;

	if (source.isOpen) {
		if (source.isSomething) {
			domObject = createSomethingCell();
		}
		else {
			domObject = createPlainCell(source.aroundSomething);
		}
	}
	else {
		domObject = createUnknownCell(source.index, doesAttachEvent);
	}

	self.domObject = domObject;
};
