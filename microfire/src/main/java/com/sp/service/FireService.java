package com.sp.service;

import org.springframework.stereotype.Service;
import com.sp.model.Fire;
import com.sp.repository.FireRepository;

@Service
public class FireService {
	
	private FireRepository fRepository;
	private UpdateRunnable uRunnable;
	private Thread displayThread;
	
	public FireService(FireRepository fRepository) {
		//Replace the @Autowire annotation....
		this.fRepository=fRepository;
		
		//Create a Runnable is charge of executing cyclic actions 
		this.uRunnable=new UpdateRunnable(this.fRepository);
		
		// A Runnable is held by a Thread which manage lifecycle of the Runnable
		displayThread=new Thread(this.uRunnable);
		
		// The Thread is started and the method run() of the associated DisplayRunnable is launch
		displayThread.start();
	}

	
	/*
	 * Methodes
	 * */
    
	/**
	 * Saves the fire to the database
	 * */
	private void addFire(Fire f) {
		fRepository.save(f); // Sauvegarde du user dans la db	
	}
	
	
	
	public Iterable<Fire> getAlldBFires() {
		Iterable<Fire> fOpt = fRepository.findAll();
		return fOpt;
	}
	
	
	

}
