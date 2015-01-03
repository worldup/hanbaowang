package com.upbest.mvc.handler;

import javax.inject.Inject;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.DefaultRowMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IBuserService;
 
@Component
public class UserRowMapperHandler implements RowMapperHandler<Buser>,InitializingBean{
	
	private DefaultRowMapper<Buser> rowMapper;
	
	@Inject
    protected UserRespository buserRepository;
	
	@Inject
	private IBuserService  userService;
   
	@Override
	public boolean handler(Buser user) {
		buserRepository.save(user);
		
		return true;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		rowMapper = new DefaultRowMapper<Buser>();
		rowMapper.setFieldSetMapper(new UserFieldSetMapper(userService));
	}
	
	@Override
	public RowMapper<Buser> getRowMapper() {
		return rowMapper;
	}
	
	public static class UserFieldSetMapper implements FieldSetMapper<Buser>{
		
		 private IBuserService  userService;
		 
		public UserFieldSetMapper(IBuserService userService){
			this.userService = userService;
		}

		@Override
		public Buser mapFieldSet(FieldSet fieldSet) throws BindException {
			String pname = fieldSet.readString(5);
			Buser puser = userService.findByName(pname);
	        
	        Buser user = new Buser();
	      
	        if(puser == null){
	            user.setPid(null);
	        }else{
	            user.setRole(fieldSet.readString(0));
	            user.setName(fieldSet.readString(1));
	            user.setPwd(fieldSet.readString(2));
	            user.setRealname(fieldSet.readString(3));
	            user.setIsdel(fieldSet.readString(4));
	            user.setPid(puser.getId());
	            user.setPic(fieldSet.readString(7));
	        }
	        return user;
		}
		
	}

}
