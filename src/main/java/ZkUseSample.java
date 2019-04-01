import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class ZkUseSample {
    private static String zkCluster = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static ZkClient zkClient = new ZkClient(zkCluster,5000);

    public static void main(String[] args){
        List<String> roots = zkClient.getChildren("/");
        for (String root : roots) {
            System.out.println(root);
        }
        zkClient.deleteRecursive("/mmt");
        zkClient.createPersistent("/mmt","zxb");
        //监听节点  subscribeChildChanges 监听当前节点以及子节点增加或者删除
        zkClient.subscribeChildChanges("/mmt", new IZkChildListener() {
            public void handleChildChange(String s, List<String> list){
                System.out.println("child change路径："+s);
                System.out.println("child change变更的节点为:"+list);
            }
        });
        //监听节点  subscribeDataChanges 监听当前节点内容的变更
        zkClient.subscribeDataChanges("/mmt", new IZkDataListener() {
            public void handleDataChange(String s, Object o){
                System.out.println("data change路径："+s);
                System.out.println("data change变更的内容为:"+o.toString());
            }

            public void handleDataDeleted(String s){
                System.out.println("data deleted路径："+s);
            }
        });
        zkClient.createPersistent("/mmt/test1","test1");
        zkClient.createPersistent("/mmt/test2","test2");
        zkClient.createPersistent("/mmt/test3","test3");
        System.out.println(zkClient.readData("/mmt"));
        System.out.println(zkClient.readData("/mmt/test1"));
        System.out.println(zkClient.readData("/mmt/test2"));
        System.out.println(zkClient.readData("/mmt/test3"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkClient.writeData("/mmt","mmt11");
        zkClient.writeData("/mmt/test1","test11");
        zkClient.writeData("/mmt/test2","test22");
        zkClient.writeData("/mmt/test3","test33");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        zkClient.delete("/mmt/test3");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        zkClient.deleteRecursive("/mmt");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
