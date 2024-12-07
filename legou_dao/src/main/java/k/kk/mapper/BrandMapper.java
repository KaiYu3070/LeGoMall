package k.kk.mapper;

import k.kk.domain.Brand;
import k.kk.searchPage.search;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/*
 *
 * Brand interface
 * */
public interface BrandMapper {

    // Get all brands
    // When executing this interface method, the MyBatis framework generates a proxy object for us. This proxy object acts like an implementation class.
    // The proxy object (implementation class) executes the corresponding SQL query from the config file and maps the fetched data into an object, which is then returned to us.
    // Each method in the interface corresponds to an SQL statement in the mapping configuration file.
    // This is the characteristic of an interface without an implementation class.
    public List<Brand> findAll();

    // Add a new brand
    void save(Brand brand);

    // Find a brand by ID
    Brand findOne(Long id);

    // Update brand information
    void update(Brand brand);

    // Delete brand by ID
    void del(Long id);

    // Search for brands based on the search criteria
    List<Brand> findBySearch(search search);
}
