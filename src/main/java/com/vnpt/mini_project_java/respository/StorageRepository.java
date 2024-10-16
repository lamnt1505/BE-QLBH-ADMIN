package com.vnpt.mini_project_java.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vnpt.mini_project_java.entity.Storage;

/*import edu.poly.Du_An_Tot_Ngiep.Entity.Imports;
*/
public interface StorageRepository extends JpaRepository<Storage,Long> {	

	@Query(value = "select * from storage where idImport = ?", nativeQuery =  true)
	public Storage findByIdStorage(long idImport);
	
}
