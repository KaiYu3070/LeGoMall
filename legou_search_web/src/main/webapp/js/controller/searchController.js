app.controller("searchController", function($scope, $location, $http) {

    // Define the object for searching by brand and category
    $scope.paramMap = {"keyword": '', "brand": '', "category": '', "page": 1};

    // Page load, initialization function
    $scope.initSearch = function() {
        // Get the 'keyword' from the URL
        // $location is another service in AngularJS, which represents the address bar
        $scope.paramMap.keyword = $location.search()["keyword"];
        // Call the search function
        $scope.search();
    }

    // Search function
    $scope.search = function() {
        // Perform the search
        $http.post("./itemSearch/search", $scope.paramMap).success(function(resp) {
            // Set the search results
            $scope.resultMap = resp.data;
            // Call the frontend pagination effect function
            buildPageLabel();
        })
    }

    // Click search button to perform the search operation
    $scope.searchByKeyword = function() {
        // Clear all search conditions
        $scope.paramMap.brand = '';
        $scope.paramMap.category = '';
        $scope.paramMap.page = 1;
        // Perform a new search
        $scope.search();
    }

    // Add a query condition to paramMap and perform the search
    $scope.addParam = function(key, value) {
        // Add property value to paramMap
        $scope.paramMap[key] = value;
        // Perform the search
        $scope.search();
    }

    // Frontend pagination effect
    function buildPageLabel() {
        $scope.pageList = [];// Add pagination property
        var maxPageNo = $scope.resultMap.totalPages; // Get the last page number
        var firstPage = 1; // Start page number
        var lastPage = maxPageNo; // End page number
        $scope.firstDot = true; // There are dots before
        $scope.lastDot = true; // There are dots after
        if ($scope.resultMap.totalPages > 5) { // If total pages are greater than 5, show part of the page numbers
            if ($scope.paramMap.page <= 3) { // If current page is less than or equal to 3
                lastPage = 5; // Show the first 5 pages
                $scope.firstDot = false; // No dots before
            } else if ($scope.paramMap.page >= lastPage - 2) { // If current page is greater than or equal to last page - 2
                firstPage = maxPageNo - 4;  // Show the last 5 pages
                $scope.lastDot = false; // No dots after
            } else { // Show 5 pages centered around the current page
                firstPage = $scope.paramMap.page - 2;
                lastPage = $scope.paramMap.page + 2;
            }
        } else {
            $scope.firstDot = false; // No dots before
            $scope.lastDot = false; // No dots after
        }
        // Loop to generate the page number labels
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageList.push(i);
        }
    }

})
