package btl.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import btl.Employee;
import btl.Pair;
import btl.Trip;
import btl.Car;

@Controller
@RequestMapping("/tk") 
public class TripController {
	private RestTemplate rest = new RestTemplate();
	
	@ModelAttribute
	public void addTripToModel(Model model) {
		
	}
	
	@GetMapping("/luong")
	public String showSalaryForm(Model model) {
		List<Employee> em = Arrays.asList(rest.getForObject("http://localhost:8080/employee/recent", Employee[].class));
		ArrayList<Pair<Employee, Long> > pair = new ArrayList<>();
		for(Employee employee : em) {
			List<Trip> trip = Arrays.asList(rest.getForObject("http://localhost:8080/trip/drive/{id}", Trip[].class, employee.getId()));
			long luong = 0;
			for(Trip t: trip) {
				if(t.getDriver().getId() == employee.getId()) {
					int level = t.getRoute().getComplicatedlevel();
					if(level==1) luong += 300000;
					else if(level==2) luong+=200000;
					else luong+=100000;
				}else {
					int level = t.getRoute().getComplicatedlevel();
					if(level==1) luong += 150000;
					else if(level==2) luong+=100000;
					else luong+=50000;
				}
			}
			pair.add( new Pair<Employee, Long>(employee, luong));
		}
		model.addAttribute("pairs", pair );
		return "tk";
	}
	
	@GetMapping("/car")
	public String showCarForm(Model model) {
		List<Car> em  = Arrays.asList(rest.getForObject("http://localhost:8080/car/recent", Car[].class));
		ArrayList<Pair<Car, Float> > pair = new ArrayList<>();
		for(Car car : em) {
			List<Trip> trip =  Arrays.asList(rest.getForObject("http://localhost:8080/trip/car/{id}", Trip[].class, car.getId()));
			float doanhthu = 0;
			for(Trip t : trip) {
				doanhthu += t.getPrice() * t.getNumberofpassengers();
			}
			pair.add( new Pair<Car, Float>(car, doanhthu));
		}
		model.addAttribute("pairs", pair);
		return "tkdoanhthu";
	}
	
}
