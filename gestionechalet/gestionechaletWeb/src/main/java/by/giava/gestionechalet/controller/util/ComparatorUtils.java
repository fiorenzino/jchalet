package by.giava.gestionechalet.controller.util;

import java.util.Comparator;

import javax.faces.model.SelectItem;

import by.giava.gestionechalet.repository.util.AlphanumComparator;


public class ComparatorUtils {

	public static final Comparator<SelectItem> SELECT_ITEMS = new Comparator<SelectItem>() {
		public int compare(SelectItem o1, SelectItem o2) {
			Comparator<Object> comparator = new AlphanumComparator();
			if (o1 == null || o1.getValue() == null)
				return Integer.MIN_VALUE;
			else
				return comparator.compare(o1.getLabel().toUpperCase(), o2
						.getLabel().toUpperCase());
		}
	};

	
}
