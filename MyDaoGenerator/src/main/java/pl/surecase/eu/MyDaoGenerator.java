package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "greendao");
        Entity task = schema.addEntity("Task");
        task.addIdProperty();
        task.addStringProperty("message");

        Entity time = schema.addEntity("Time");
        time.addIdProperty();
        time.addDateProperty("alert").notNull();
        time.addBooleanProperty("ack");
        time.addStringProperty("note");
        Property timeFKtask = time.addLongProperty("task_id").notNull().getProperty();

        time.addToOne(task, timeFKtask);
        task.addToMany(time, timeFKtask);

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
