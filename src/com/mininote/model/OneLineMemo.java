package com.mininote.model;

import java.io.Serializable;
import java.util.Comparator;

public class OneLineMemo implements Serializable {
	private boolean isImportant;
	private boolean toDo;
	private String memo;
	private String creationDate;
	
	public OneLineMemo() {}

	public OneLineMemo(boolean isImportant, boolean toDo, String memo, String creationDate) {
		this.isImportant = isImportant;
		this.toDo = toDo;
		this.memo = memo;
		this.creationDate = creationDate;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public boolean isToDo() {
		return toDo;
	}

	public void setToDo(boolean toDo) {
		this.toDo = toDo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	

}

//class MemoCompare implements Comparator<OneLineMemo> {
//    int ret = 0;
//    
//    @Override
//    public int compare(OneLineMemo m1, OneLineMemo m2) {
//        if(s1.num < s2.num) {
//            ret = -1;
//        }
//        if(s1.num == s2.num) {
//            if(s1.name.compareTo(s2.name) == 0) {
//                if(s1.address.compareTo(s2.address) < 0) {
//                    ret = 1;
//                } else if(s1.address.compareTo(s2.address) == 0) {
//                    ret = 0;
//                } else if(s1.address.compareTo(s2.address) > 0) {
//                    ret = -1;
//                }
//            } else if(s1.name.compareTo(s2.name) < 0) {
//                ret = -1;
//            } else if(s1.name.compareTo(s2.name) > 0) {
//                ret = 1;
//            }
//        }
//        if(s1.num > s2.num) {
//            ret = 1;
//        }
//        return ret;
//    }
//}
//
//
//List<Abc> abc = new ArrayList<Abc>();
//
////add entries here
//
////now sort them
//Collections.sort(abc, new Comparator<Abc>(){
//      @Override
//      public int compare(Abc abc1, Abc abc2){
//
//          boolean b1 = abc1.isClickable;
//          boolean b2 = abc2.isClickable;
//
//          if (b1 == !b2){
//              return 1;
//          }
//          if (!b1 == b2){
//              return -1;
//          }
//          return 0;
//      }
//  });
