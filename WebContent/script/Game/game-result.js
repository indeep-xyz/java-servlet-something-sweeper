document.addEventListener('DOMContentLoaded', function(){
  var fieldManager = new FieldManager('field', true);
  
  fieldManager.loadField();
  
  HistoryLoader.loadAll(function(historyData) {
    var historyViewer = new HistoryViewer(historyData, {
        'field': 'field',
        'fieldManager': fieldManager,
        'switchMode': 'replay-mode',
        'operatorWrapper': 'replay-operator-wrapper',
        'reset': 'replay-reset-controller',
        'next': 'replay-next-controller',
        'previous': 'replay-previous-controller',
    });
  });
});
