
package mp3player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class Playlist {
JFileChooser fc=new JFileChooser();
ArrayList ls=new ArrayList();

void add(JFrame frame){
fc.setMultiSelectionEnabled(true);
int fileValid=fc.showOpenDialog(frame);
if(fileValid==javax.swing.JFileChooser.CANCEL_OPTION){
return;
} else if(fileValid==javax.swing.JFileChooser.APPROVE_OPTION)
{
File[] file=fc.getSelectedFiles();
ls.addAll(Arrays.asList(file));

}
}
ArrayList getListSong(){
return ls;

}
//save playlist
FileOutputStream fos;
ObjectOutputStream oos;
public void saveAsPlaylist(JFrame frame){
fc.setMultiSelectionEnabled(true);
int Valid=fc.showOpenDialog(frame);
if(Valid==javax.swing.JFileChooser.CANCEL_OPTION){
return;
} else if(Valid==javax.swing.JFileChooser.APPROVE_OPTION)
{
File[] pls=fc.getSelectedFiles();
try{

fos=new FileOutputStream(pls + ".tgr");
oos=new ObjectOutputStream(fos);

                for (int i=0; i<ls.size() ; i++){
                    File tmp = (File) ls.get(i);
                    oos.writeObject(tmp);
                }
                oos.close();

}catch(Exception e){

}

}
}

    FileInputStream fis;
    ObjectInputStream ois;
    
    public void openPls(JFrame frame){
        fc.setMultiSelectionEnabled(false);
        int Valid = fc.showOpenDialog(frame);
        if (Valid == javax.swing.JFileChooser.CANCEL_OPTION){
            return;
        }
        if (Valid == javax.swing.JFileChooser.APPROVE_OPTION){
            File pls = fc.getSelectedFile();
            try{
                fis = new FileInputStream(pls);
                ois = new ObjectInputStream(fis);
                File tmp;
                while ((tmp = (File) ois.readObject()) != null){
                    ls.add(tmp);
                }
              if ((tmp = (File) ois.readObject()) == null){
                  ls.isEmpty();
              }
              ois.close();
            }catch (Exception e){
        }
        
        }
    }



}
