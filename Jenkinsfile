pipeline {
	agent none
	tools {
    	maven 'Maven'
	}
	stages {
    	stage("Checkout") {   
        	agent { label 'Slave-1' }
        	steps {               	 
            	git url: 'https://github.com/ergaurav21/EMBL.git'         	 
           	 
        	}    
    	}
    	stage('Build') {
        	agent { label 'Slave-1' }
        	steps {
          
        	sh "mvn clean compile"  	 
        	}
    	}
		
		
   	 
    	stage("Unit test") {          	 
        	agent { label 'Slave-1' }
        	steps {  	 
            	git url:  'https://github.com/ergaurav21/EMBL.git'
            
                 sh "docker-compose up -d"
            	sh "mvn test"  
			
       	}  
	
    	}
		stage("Post clean up") {   
			agent { label 'Slave-1' }
        	  steps {  	 
            
            	
                    sh "docker-compose down"
            	
			
         	 }  
	
    	    }
		
		stage("next build") {   
			agent { label 'Slave-1' }
        	  steps {  	 
            
            	
                  build 'Proximity Build'
			  echo 'develop build running'
            	
			
         	 }  
	
    	    }
		
		
 
		
	}

}
