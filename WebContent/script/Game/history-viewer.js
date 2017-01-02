/**
 * Manage to view history data in game.
 *
 * @class
 * @constructor
 */
var HistoryViewer = function(history, options){
	this.history = history;
	this.fieldId = options.field;
	this.fieldManager = options.fieldManager;

	this.initControls(options);
};

/**
 * History data in a current game.
 *
 * @public
 * @var {Object}
 */
HistoryViewer.prototype.history = {};

/**
 * An ID of a HTML element as a game field.
 *
 * @public
 * @var {string}
 */
HistoryViewer.prototype.fieldId = '';

/**
 * Order number of the history property.
 *
 * @public
 * @var {Number}
 */
HistoryViewer.prototype.orderNumber = undefined;

/**
 * Initialize HTML elements as controls of HistoryViewer.
 * 
 * @public
 * @method
 * @param {object} options - A hash array having IDs of controls for initialization 
 */
HistoryViewer.prototype.initControls = function(options) {
	// - - - - - - - - - - - - - - - -
	// private variables - in HistoryViewer.prototype.initControls

	/**
	 * An instance of HistoryView.
	 *
	 * @private
	 * @var
	 */
	var self = this;
	
	// - - - - - - - - - - - - - - - -
	// private functions - in HistoryViewer.prototype.initControls

	/**
	 * Initialize a HTML check box element as a controller
	 * which switches the mode of replay or not. 
	 *
	 * @private
	 * @method
	 * @param {string} id - An ID attribute of a HTML element to initialize
	 */
	function initControlToSwitchMode(idSwitcher, idOperatorWrapper) {
		var switcher = document.getElementById(idSwitcher);
		var operatorWrapper = document.getElementById(idOperatorWrapper);

		if (typeof switcher === 'object'
				&& typeof operatorWrapper === 'object') {
			operatorWrapper.className = 'replay operator-wrapper disabled';
			
			switcher.addEventListener('change', function() {
				if (switcher.checked) {
					switcher.checked = true;
					operatorWrapper.className = 'replay operator-wrapper enabled';
					self.reset();
					self.resetView();
				}
				else {
					switcher.checked = false;
					operatorWrapper.className = 'replay operator-wrapper disabled';
					self.fieldManager.refreshFieldView();
				}
			});
		}
	}

	/**
	 * Initialize a HTML element as a controller
	 * which resets the order number and refreshes the view. 
	 *
	 * @private
	 * @method
	 * @param {string} id - An ID attribute of a HTML element to initialize
	 */
	function initControlToReset(id) {
		var controller = document.getElementById(id);

		if (typeof controller === 'object') {
			controller.className = 'replay reset';
			controller.innerHTML = 'reset';
			
			controller.addEventListener('click', function() {
				self.reset();
				self.resetView();
			});
		}
	}

	/**
	 * Initialize a HTML element as a controller
	 * which increases the order number and refresh the view.
	 *
	 * @private
	 * @method
	 * @param {string} id - An ID attribute of a HTML element to initialize
	 */
	function initControlToMoveNext(id) {
		var controller = document.getElementById(id);

		if (typeof controller === 'object') {
			controller.className = 'replay next';
			controller.innerHTML = 'next';
	
			controller.addEventListener('click', function() {
				self.next()
				
				var openedCells = self.recentOpenedCells();
				self.updateView(openedCells);
			});
		}
	}

	/**
	 * Initialize a HTML element as a controller
	 * which decreases the order number and refresh the view.
	 *
	 * @private
	 * @method
	 * @param {string} id - An ID attribute of a HTML element to initialize
	 */
	function initControlToMovePrevious(id) {
		var controller = document.getElementById(id);
		
		if (typeof controller === 'object') {
			controller.className = 'replay previous';
			controller.innerHTML = 'previous';
	
			controller.addEventListener('click', function() {
				self.previous()
				var openedCells = self.currentOpenedCells();
				
				self.resetView();
				self.updateView(openedCells);
			});
		}
	}

	// - - - - - - - - - - - - - - - -
	// main - in HistoryViewer.prototype.initControls

	initControlToSwitchMode(options.switchMode, options.operatorWrapper);
	initControlToMoveNext(options.next);
	initControlToMovePrevious(options.previous);
	initControlToReset(options.reset);
}

/**
 * Reset the order number.
 * 
 * @public
 * @method
 */
HistoryViewer.prototype.reset = function() {
	this.orderNumber = -1;
};

/**
 * Extract the current history data.
 * 
 * @public
 * @method
 */
HistoryViewer.prototype.currentHistory = function() {
	return this.history.records[this.orderNumber];
};

/**
 * Extract data of all opened cells at current.
 * 
 * @public
 * @method
 * @return {array} Data of all opened cells at current
 */
HistoryViewer.prototype.currentOpenedCells = function() {
	var openedCells = [];

	for (var i = 0; i < this.orderNumber + 1; i++) {
		openedCells = openedCells.concat(this.history.records[i].record.openedCells);
	}

	return openedCells;
};

/**
 * Extract data of opened cells at a current record of the history data.
 * 
 * @public
 * @method
 * @return {array} Data of opened cells at a current record of the history data
 */
HistoryViewer.prototype.recentOpenedCells = function() {
	var currentHistory = this.currentHistory();
	var openedCells = [];

	if (typeof currentHistory !== 'undefined') {
		openedCells = currentHistory.record.openedCells;
	}

	return openedCells;
};

/**
 * Increase the order number for the history data.
 * 
 * @public
 * @method
 * @return True when the order number increased, else false
 */
HistoryViewer.prototype.next = function() {
	var hasIncreased = false;

	if (this.orderNumber < this.history.records.length - 1) {
		this.orderNumber++;
		hasIncreased = true;
	}
	
	return hasIncreased;
};

/**
 * Decrease the order number for the history data.
 * 
 * @public
 * @method
 * @return True when the order number decreased, else false
 */
HistoryViewer.prototype.previous = function() {
	var hasDecreased = false;
	
	if (-1 < this.orderNumber) {
		this.orderNumber--;
		hasDecreased = true;
	}

	return hasDecreased;
};

/**
 * Update the field view with data of opened cells.
 * 
 * @public
 * @method
 * @param {array} openedCellDataArray - Data of cells. 
 */
HistoryViewer.prototype.updateView = function(cellDataArray) {
	var fieldManager = new FieldManager(this.fieldId, null);

	console.info(this.orderNumber);
	console.info(cellDataArray);
	
	fieldManager.updateViewByCellDataArray(cellDataArray);
};

/**
 * Reset the field view.
 * 
 * @public
 * @method
 */
HistoryViewer.prototype.resetView = function() {
	var fieldManager = new FieldManager(this.fieldId, null);
	fieldManager.resetView();
};
