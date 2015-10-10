package imported_from_db;

import imported_from_db.Categories;
import imported_from_db.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-10T16:00:03")
@StaticMetamodel(Operations.class)
public class Operations_ { 

    public static volatile SingularAttribute<Operations, Date> date;
    public static volatile SingularAttribute<Operations, Integer> amount;
    public static volatile SingularAttribute<Operations, Integer> id;
    public static volatile SingularAttribute<Operations, Users> userId;
    public static volatile SingularAttribute<Operations, Categories> categoryId;

}