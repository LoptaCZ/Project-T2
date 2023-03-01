package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Research{
    public enum Difficulty{
        NULL(0,"null"),
        TRIVIAL(1,"trivial"),
        EASY(2,"easy"),
        MODERATE(3,"moderate"),
        HARD(4,"hard"),
        TRICKY(5,"tricky"),
        TORTUROUS(6,"torturous");
        Difficulty(int index,String name){

        }
    }
    private static final Map<String,ArrayList<Object>>researchProjects=new HashMap<>();
    /*  Project Name |  0 - Category [ BYTE ] { 0 1 2 3 4 } Unsorted Lost Forbidden Tainted Eldritch
     *                  1 - Requisite [ STRING ] %any%
     *                  2 - Result [ OBJECT ] { ItemStack | Enchant }
     *                  3 - Difficulty is not fully implemented cuz it's randomized { Offset: ~1 }
     */
    public void initResearch(){
        if(researchProjects!=null)
            if(!(researchProjects instanceof HashMap))ProjectT2.LOGGER.fatal("Research cannot be other instance than HashMap!");
        else ProjectT2.LOGGER.fatal("Research is null!? This shouldn't ever happened.");

        if(researchProjects.isEmpty()){
            //      UNSORTED
            addProject("research_book",(byte)0,Difficulty.TRIVIAL.ordinal(),"",new ItemStack(PT2Items.BOOK.get()));
            addProject("mask",(byte)0,Difficulty.TRIVIAL.ordinal(),"",new ItemStack(PT2Items.MASK_CRUELTY.get()));
            addProject("dawnstone",(byte)1,Difficulty.TRIVIAL.ordinal(),"",new ItemStack(PT2Items.DAWN_STONE.get()));
            //addProject("",(byte)0,"",0,new ItemStack(Items.AIR));
            //addProject("",(byte)0,"",0,new ItemStack(Items.AIR));

            //      LOST
            //      FORBIDDEN
            //      TAINTED
            //      ELDRITCH
        }
    }

    public static void addProject(String projectName,byte category,int difficulty,String requisite,ItemStack result){
        if(!researchProjects.containsKey(projectName)){
            ArrayList<Object>list=new ArrayList<>();
            list.set(0,category);
            list.set(1,requisite);
            list.set(2,result);
            list.set(3,difficulty);
            researchProjects.put(projectName,list);
        }else ProjectT2.LOGGER.error("Trying to insert already existing research project {}.",projectName);
    }
    public static void addProject(String projectName,byte category,int difficulty,String requisite,Enchantment result){
        if(!researchProjects.containsKey(projectName)){
            ArrayList<Object>list=new ArrayList<>();
            list.set(0,category);
            list.set(1,requisite);
            list.set(2,result);
            list.set(3,difficulty);
            researchProjects.put(projectName,list);
        }else ProjectT2.LOGGER.error("Trying to insert already existing research project {}.",projectName);
    }

    public static boolean validResearch(String project){
        if(researchProjects.containsKey(project)){
            if((byte)researchProjects.get(project).get(0)>0){
                if ((int) researchProjects.get(project).get(1) != -1){
                    if (!((String) researchProjects.get(project).get(3)).isEmpty()){
                        if (researchProjects.get(project).get(2) instanceof ItemStack is){
                            ProjectT2.LOGGER.error("Invalid Research! Result Item is null!");
                            return is != ItemStack.EMPTY;
                        }else if(researchProjects.get(project).get(2) instanceof Enchantment en){
                            return true;
                        }else{
                            ProjectT2.LOGGER.error("Invalid Research! Not a supported result Thing! [ ItemStack | Enchantment ]");
                            return false;
                        }
                    }else ProjectT2.LOGGER.error("Invalid Research! Missing Result Thing [ ItemStack | Enchantment ]");
                }else ProjectT2.LOGGER.error("Invalid Research! Invalid Difficulty [ 0 - 6 ]");
            }else ProjectT2.LOGGER.error("Invalid Research! Invalid Category [ 0 - 4 ] ");
        }
        ProjectT2.LOGGER.error("Invalid Research! Research does not exist!");
        return false;
    }
    public String getProjectRequisite(String project){
        return (String)researchProjects.get(project).get(2);
    }
    public byte getProjectCategory(String project){
        return (byte)researchProjects.get(project).get(0);
    }
    public int getProjectDifficulty(String project){
        try{
            return (int)researchProjects.get(project).get(1);
        }catch(Exception ignored){}
        return 0;
    }
    public ItemStack getProjectResult(String project){
        return (ItemStack)researchProjects.get(project).get(3);
    }
}
