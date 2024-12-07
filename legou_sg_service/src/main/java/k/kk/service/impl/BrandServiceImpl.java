package k.kk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import k.kk.domain.Brand;
import k.kk.mapper.BrandMapper;
import k.kk.searchPage.search;
import k.kk.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Transaction annotation
@Transactional
// Add annotation to publish the service and place the object into the IOC container
@Service
public class BrandServiceImpl implements BrandService {

    // Inject BrandMapper
    @Autowired
    BrandMapper brandMapper;

    /*
     * Find all brands
     * */
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    /*
     * Pagination query
     * */
    @Override
    public PageInfo<Brand> findPage(int pageNum, int pageSize) {
        // Set the current page and page size first. The pagination plugin provides a method to do this.
        // PageHelper.startPage(pageNum, pageSize) is the static method to set the page number and page size.
        PageHelper.startPage(pageNum, pageSize);

        // Query all data. The "findAll" method is used here for pagination,
        // and the plugin automatically generates the SQL query for pagination.
        List<Brand> brandList = brandMapper.findAll();

        // Wrap the data in a PageInfo object
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        System.out.println(pageInfo.getList());
        return pageInfo;
    }

    /*
     * Add a new brand
     * */
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    /*
     * Find a brand by its ID
     * */
    @Override
    public Brand findOne(Long id) {
        return brandMapper.findOne(id);
    }

    /*
     * Update brand data
     * */
    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    /*
     * Delete brands by their IDs
     * */
    @Override
    public void del(Long[] ids) {
        // Iterate over the IDs and delete
        for (Long id : ids) {
            // Delete the brand by ID
            brandMapper.del(id);
        }
    }

    /*
     * Conditional query
     * */
    @Override
    public PageInfo<Brand> searchPage(int pageNum, int pageSize, search search) {
        PageHelper.startPage(pageNum, pageSize);

        // Perform search query
        List<Brand> brandList = brandMapper.findBySearch(search);

        // Wrap the data in a PageInfo object
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        System.out.println(pageInfo.getList());
        return pageInfo;
    }

}
