// Define brandController
app.controller("brandController", function($scope, $controller, $http) {

    // Controller inheritance, essentially sharing the same $scope
    $controller('baseController', { $scope: $scope });

    // Define reloadList method
    $scope.reloadList = function() {
        // Send request to query data with pagination
        // Request parameters: current page and items per page
        // Response format: data = {total: xx, rows: [{}, {}]}
        $http.get("../brand/findPage/" + $scope.paginationConf.currentPage + "/" + $scope.paginationConf.itemsPerPage).success(function(resp) {
            if (resp.success) {
                // Paginated data
                $scope.list = resp.data;
                // Total records
                $scope.paginationConf.totalItems = resp.total;
            } else {
                // Failure
                alert(resp.message);
            }
        });
    };

    // Save and update method
    $scope.save = function() {

        // Define method type
        var method = "save";
        // Check if it's an update based on the presence of ID
        if ($scope.entity.id != null) {
            // If ID exists, perform update
            method = "update";
        }

        // Send POST request to save data
        $http.post("../brand/" + method, $scope.entity).success(function(resp) {
            if (resp.success) {
                // Reload the data
                $scope.reloadList();
                // Clear the entity
                $scope.entity = {};
            } else {
                alert(resp.message);
            }
        });
    };

    // Find one by ID method
    $scope.findOne = function(id) {
        // Query by primary key
        $http.get("../brand/findOne/" + id).success(function(resp) {
            // Assign the result to the entity variable
            $scope.entity = resp.data;
        });
    };

    // Delete method
    $scope.dele = function() {
        // Check if any item is selected
        if ($scope.selectIds.length == 0) {
            return;
        }

        // Confirmation prompt
        if (window.confirm("Are you sure to delete?")) {
            // Send request to delete data
            $http.get("../brand/delete?ids=" + $scope.selectIds).success(function(resp) {
                if (resp.success) {
                    // Reload the data
                    $scope.reloadList();
                    // Clear selected IDs
                    $scope.selectIds = [];
                } else {
                    alert(resp.message);
                }
            });
        }
    };

});
