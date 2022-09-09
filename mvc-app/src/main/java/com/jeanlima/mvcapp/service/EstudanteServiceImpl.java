package com.jeanlima.mvcapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jeanlima.mvcapp.model.Estudante;

@Component
public class EstudanteServiceImpl implements EstudanteService{

    private List<Estudante> estudantes;

    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    @Override
    public void salvarEstudante(Estudante estudante) {
        this.estudantes.add(estudante);
        
    }

    @Override
    public void deletarEstudante(Estudante estudante) {
        
        this.estudantes.remove(estudante);
        
    }

    @Override
    public Estudante getEstudanteById(Integer id) {
        for (Estudante estudante : estudantes) {
            if(estudante.getId() == id){
                return estudante;
            }
        }
        return null;
    }

	@Override
	public List<Estudante> getEstudanteByCurso(String curso) {
		List <Estudante> estudantesTemp = new ArrayList<Estudante>();
		for (Estudante e : estudantes) {
			if (e.getCurso() == curso) estudantesTemp.add(e);
		}
		return estudantesTemp;
	}

	@Override
	public List<Estudante> getEstudanteByLanguage(String language) {
		List <Estudante> estudantesTemp = new ArrayList<Estudante>();
		for (Estudante e : estudantes) {
			if (e.getLinguagem() == language) estudantesTemp.add(e);
		}
		return estudantesTemp;
	}
    
}
