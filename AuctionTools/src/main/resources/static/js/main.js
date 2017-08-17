
	
	
	function deleteUser()
	{
		$("#error").hide();
		
		
		$(document).ready(function(){
			username = $("#username").val();
			password = $("#password").val();
			
			validate(username,password);
				
					$.ajax({
						type: 'DELETE',
						url: '/user/deleteuser?username='+username+"&password="+password,
						async: false,
						success: function(res){
							if(res != null && res != "success")
								{
									$("#error").html(res);
									$("#error").show();
								}
							else
								{
								$("#success").html(res);
								$("#success").show();
								}
						}
						
					});
			
			
			
		});
	}
	
	function validate(username,password)
	{
		if(username.length <= 0 && password.length <= 0)
			{
				return;
			}
		else
			{
			//show error
			}
		
	}
