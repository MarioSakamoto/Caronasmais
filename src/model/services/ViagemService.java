package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Viagem;

public class ViagemService {
	
	public List<Viagem> findAll() {
		List<Viagem> list = new ArrayList<>();
		list.add(new Viagem ("16/09/2022", "11:00", "Monte Carmelo", "Uberl�ndia"));
		list.add(new Viagem ("16/09/2022", "14:00", "Uberl�ndia", "Monte Carmelo"));
		list.add(new Viagem ("17/09/2022", "12:00", "Monte Carmelo", "Uberl�ndia"));
		list.add(new Viagem ("17/09/2022", "15:00", "Uberl�ndia", "Monte Carmelo"));
		return list;
	}
		
}


