package org.jtest.app.dao.item;



import java.util.List;

import org.jtest.app.model.item.ProjectTreeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ProjectTreeItemDao extends JpaRepository<ProjectTreeItem, Long>{
	
	public ProjectTreeItem findByitemType(String itemType);
	public ProjectTreeItem findByid(Long id);
	public List<ProjectTreeItem> findByparentid(String parentid);
	@Modifying
	@Transactional
    @Query(value="UPDATE ProjectTreeItem im SET im.haschildren= :children WHERE im.id= :id")
	public int updatehasChildrenByid(@Param("children") boolean children,@Param("id")Long id);
	
	@Modifying
	@Transactional
    @Query(value="UPDATE ProjectTreeItem im SET im.text= :text WHERE im.id= :id")
	public int updateitemtextByid(@Param("text") String text,@Param("id")Long id);
	
}
