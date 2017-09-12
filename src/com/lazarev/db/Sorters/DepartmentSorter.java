package com.lazarev.db.Sorters;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.lazarev.db.entity.Department;

public class DepartmentSorter {

	private static final Logger LOGGER = Logger.getLogger(DepartmentSorter.class);

	public static List<Department> sort(String sortType, List<Department> departments) {
		if (sortType == null) {
			LOGGER.error("can not sort departmens sort cause is null");
		} else {
			switch (sortType) {
			case "name a-z":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				break;
			case "name z-a":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o2.getName().compareTo(o1.getName());
					}
				});
				break;
			case "gov>":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o2.getPlaceDov() - o1.getPlaceDov();
					}
				});
				break;
			case "gov<":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o1.getPlaceDov() - o2.getPlaceDov();
					}
				});
				break;
			case "total>":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o2.getTotaPlace() - o1.getTotaPlace();
					}
				});
				break;
			case "total<":
				Collections.sort(departments, new Comparator<Department>() {
					@Override
					public int compare(Department o1, Department o2) {
						return o1.getTotaPlace() - o2.getTotaPlace();
					}
				});
				break;
			default:
				LOGGER.error("can not sort departmens by" + sortType);
				break;
			}
		}
		return departments;
	}
}
