/**
 * A cell.
 *
 * @class
 * @constructor
 * @param {FieldManager} fieldManager - An instance to manage field
 * @param {object} source - Parameters to create a cell
 */
var Cell = function(fieldManager, source){
	this.fieldManager = fieldManager;
	this.source = source;

	self.index = source.index;
	self.isOpen = source.isOpen;

	this.initDomObject(source);
};

/**
 * An instance to manage field.
 *
 * @public
 * @var {FieldManager}
 */
Cell.prototype.fieldManager = undefined;

/**
 * Parameters to create a cell.
 *
 * @public
 * @var {object}
 */
Cell.prototype.source = undefined;

/**
 * An ID of the cell.
 *
 * @public
 * @var {number}
 */
Cell.prototype.index = undefined;

/**
 * A status that the cell is open or not.
 *
 * @public
 * @var {boolean}
 */
Cell.prototype.isOpen = undefined;

/**
 * A DOM object as a cell.
 *
 * @public
 * @var {HTMLElement}
 */
Cell.prototype.domObject = undefined;

/**
 * Initialize a DOM object in an instance.
 *
 * @public
 * @method
 * @param  {object} source - Parameters to create a cell
 */
Cell.prototype.initDomObject = function(source) {
	// - - - - - - - - - - - - - - - -
	// private variables - in Cell.prototype.initDomObject

	/**
	 * An instance of FieldManager.
	 *
	 * @private
	 * @var {FieldManager}
	 */
	var self = this;

	// - - - - - - - - - - - - - - - -
	// private functions - in Cell.prototype.initDomObject

	/**
	 * Create a plain cell.
	 *
	 * @private
	 * @method
	 * @param  {number} aroundSomething - The number of something cells around a cell
	 * @return {HTMLElement} A plain cell
	 */
	function createPlainCell(aroundSomething) {
		var cell = document.createElement('div');
		cell.className = 'cell opened plain';

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
		var cell = document.createElement('div');
		cell.className = 'cell opened something';

		return cell;
	}

	/**
	 * Create a cell which does not open yet.
	 *
	 * @private
	 * @method
	 * @param  {number} index - An ID of a cell
	 * @return {HTMLElement} An unknown cell
	 */
	function createUnknownCell(index) {
		var cell = document.createElement('div');
		cell.className = 'cell clickable';

		cell.addEventListener('click', function() {
			// console.info('[Cell.prototype.initDomObject#createUnknownCell] index = ' + index);
			self.fieldManager.loadField(index);
		});

		return cell;
	}

	// - - - - - - - - - - - - - - - -
	// main - in Cell.prototype.initDomObject

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
		domObject = createUnknownCell(source.index);
	}

	self.domObject = domObject;
};