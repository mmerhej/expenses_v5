package imported_from_db;

import imported_from_db.Operations;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-14T22:08:15")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> fname;
    public static volatile SingularAttribute<Users, Integer> privileges;
    public static volatile SingularAttribute<Users, String> lname;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile ListAttribute<Users, Operations> operationsList;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> username;

}