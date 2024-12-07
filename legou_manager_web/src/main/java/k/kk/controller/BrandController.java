package k.kk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import k.kk.domain.Brand;
import k.kk.entity.Result;
import k.kk.searchPage.search;
import k.kk.service.BrandService;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// @RestController = @Controller + @ResponseBody
@RestController
// Configuring request path mapping
@RequestMapping("/brand")
public class BrandController {

    // Injecting the service
    @Reference
    private BrandService brandService;

    /*
     * Get all brand data
     * */
    @RequestMapping("/findAll")
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    /*
     * Using RESTful style design (for method parameters)
     * RESTful style path: /{pageNum}/{pageSize}
     * To access this, use: /brand/{pageNum}/{pageSize}
     * This method handles paginated queries. The front-end should send two parameters: current page and page size.
     * */
    @RequestMapping("/findPage/{pageNum}/{pageSize}")
    public Result findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        System.out.println(pageNum);
        System.out.println(pageSize);
        System.out.println("-------------------");
        try {
            PageInfo<Brand> pageInfo = brandService.findPage(pageNum, pageSize);
            // Success
            System.out.println(pageInfo.getList());
            return new Result(true, "Query successful", pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            // Print exception
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /*
     * Save brand data
     * */
    @RequestMapping("/save")
    public Result save(@RequestBody Brand brand) {
        try {
            // Call the service layer
            brandService.save(brand);
            // Success
            return new Result(true, "Addition successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Addition failed");
        }
    }

    /*
     * Get brand data by ID
     * */
    @RequestMapping("/findOne/{id}")
    public Result findOne(@PathVariable("id") Long id) {
        try {
            // Call the service layer
            Brand brand = brandService.findOne(id);
            // Success
            return new Result(true, "Operation successful", brand);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Update brand data
     * */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            // Call the service layer
            brandService.update(brand);
            // Success
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Delete brand data
     * */
    @RequestMapping("/del")
    public Result del(Long[] ids) {
        try {
            // Call the service layer
            brandService.del(ids);
            // Success
            return new Result(true, "Operation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

    /*
     * Conditional search for brands
     * */
    @RequestMapping("/searchPage/{pageNum}/{pageSize}/{name}/{firstChar}")
    public Result searchPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,
                             @PathVariable("name") String name, @PathVariable("firstChar") String firstChar) {
        search search = new search();

        if (!"null".equals(name)) {
            search.setBname(name);
        }

        if (!"null".equals(firstChar)) {
            search.setFirstChar(firstChar);
        }

        try {
            // Call the service layer
            PageInfo<Brand> pageInfo = brandService.searchPage(pageNum, pageSize, search);

            System.out.println("---------------Success--------------");
            for (Brand brand : pageInfo.getList()) {
                System.out.println(brand);
            }
            return new Result(true, "Query successful", pageInfo.getTotal(), pageInfo.getList());
        } catch (Exception e) {
            System.out.println("----------------Error-------------");
            e.printStackTrace();
            return new Result(false, "Query failed");
        }
    }

    /*
     * Get all brand data for template addition
     * */
    @RequestMapping("/findBrandList")
    public Result findBrandList() {
        try {
            // Create a list to store the results
            List<Map> list = new ArrayList<>();
            // Get all brand data and format it into a list
            List<Brand> brandList = brandService.findAll();
            // Build the data for the response
            for (Brand brand : brandList) {
                Map map = new HashMap();
                map.put("id", brand.getId());
                map.put("text", brand.getName());
                list.add(map);
            }

            // Success
            return new Result(true, "Operation successful", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "Operation failed");
        }
    }

}
