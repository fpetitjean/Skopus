package skopus;
/*******************************************************************************
 * Copyright (C) 2015 Tao Li
 * 
 * This file is part of Skopus.
 * 
 * Skopus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * Skopus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Skopus.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
import java.util.Comparator;

//�����͵�item��event�Ļ���ԭʼ
public class ItemQElem {
	public int item;
	public double ubvalue = 0;
	//public double ubval = 0.0;
	
	public boolean equals(final ItemQElem e){
		if(this.item != e.item)
			return false;
//		if(this.covernumber != e.covernumber)
//			return false;
		return true;
	}
	
	public String toString() {
		String strResult = new String();
		strResult += "<";
		strResult += GlobalData.alItemName.get(item) ;
		strResult += ">";

		// strResult += "\t" + covernumber;

		return strResult;
	}

}

