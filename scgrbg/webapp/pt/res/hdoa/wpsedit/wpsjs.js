 

function Wps(DocFrame)
{
	this.DocFrame = DocFrame;
}


//光标右移
Wps.prototype.moveRight = function()
{
	try{
		var ret = this.DocFrame.ActiveDocument.ActiveWindow.Selection.moveRight();
		if (ret > 0) 
			return true;
		else
			return false;
	}
	catch(e)
	{
		return false;
	}
}
//插入段落符
Wps.prototype.insertParagraph = function()
{
	try{
		this.DocFrame.ActiveDocument.ActiveWindow.Selection.TypeParagraph();
		return true;
	}
	catch(e)
	{
		return false;
	}
}
//设置字体大小
Wps.prototype.setFontSize = function(size)
{
	try{
		var ret = this.DocFrame.ActiveDocument.ActiveWindow.Selection.Font.Size = size;
		if (ret == size)
			return true;
		else
			return false;
	}
	catch(e)
	{
		return false;
	}		
}
//插入附件接口
Wps.prototype.addAttachment = function(n,FJpath,FJSMpath)
{
	var ret,ret1,ret2,ret3;
	try
	{
		var isExists = this.DocFrame.isExists("附件说明");
		var isExists_2 = this.DocFrame.isExists("附注");
		if (n == 1 && !isExists && isExists_2)//模板中没有附件,有附注
		{
			this.DocFrame.DocObj.Application.Selection.EndKey();
			ret = this.DocFrame.cursorToDocumentField("附注",2);  //附注后插入附件
			//光标右移
			ret = this.DocFrame.ActiveDocument.ActiveWindow.Selection.moveRight();
			if (ret)
			{
				this.DocFrame.DocObj.Application.Selection.InsertBreak();//undefine
				this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJpath);
				ret1 = this.renameDocField("附件_0","附件");
				ret2 = this.renameDocField("FJ_标题_0","FJ_标题");
				ret3 = this.renameDocField("FJ_正文_0","FJ_正文");
				if (!ret1 || !ret2 || !ret3)
					return false;
				
				ret1 = this.DocFrame.cursorToDocumentField("正文",2);
				if (!ret1)
					return false;
				this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJSMpath);  //正文后插入附件说明
				//var value = this.DocFrame.DocObj.DocumentFields("FJ_标题").Value;
				//ret1 = this.DocFrame.setDocumentField("附件说明",value);
				this.DocFrame.setDocumentField("附件",'附件');
				if (!ret1)
					return false;
				
				//删除多余行
				this.DocFrame.cursorToDocumentField("附件说明",2);
				this.DocFrame.DocObj.Application.Selection.MoveDown(5,1,0);  
				for(var k=0; k<2; k++)
				{
					var count = this.DocFrame.DocObj.Application.Selection.Paragraphs.Count;
					for (var i = count;i > 0;i--)
						this.DocFrame.DocObj.Application.Selection.Paragraphs(i).Range.Delete();
				}

				return true;
			}
		}
		if (n == 1 && !isExists && !isExists_2)//模板中没有附件,没有附注
		{
			
			this.DocFrame.DocObj.Application.Selection.EndKey();

			var isExists_3 = this.DocFrame.isExists("成文日期");
			var isExists_4 = this.DocFrame.isExists("印发日期");
			if(isExists_3) //模板10和17
			{
				ret = this.DocFrame.cursorToDocumentField("成文日期",2);  
				//光标下移
				ret = this.DocFrame.ActiveDocument.ActiveWindow.Selection.MoveDown(5,1,0); 
				this.DocFrame.DocObj.Application.Selection.InsertBreak();//undefine
				this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJpath);
			}
			else if(!isExists_3 && isExists_4)  //模板7
			{
				this.DocFrame.cursorToDocumentField("抄送机关_1",1);
				ret = this.DocFrame.ActiveDocument.Application.Selection.MoveUp();
				ret = this.DocFrame.ActiveDocument.Application.Selection.MoveUp();
				this.DocFrame.DocObj.Application.Selection.InsertBreak();//undefine
				this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJpath);
			}
			else  //模板6
			{
				this.DocFrame.cursorToDocumentField("正文",2);
				this.DocFrame.DocObj.Application.Selection.InsertBreak();//undefine
				this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJpath);
			}
			
			ret1 = this.renameDocField("附件_0","附件");
			ret2 = this.renameDocField("FJ_标题_0","FJ_标题");
			ret3 = this.renameDocField("FJ_正文_0","FJ_正文");
			if (!ret1 || !ret2 || !ret3)
				return false;
		
			ret1 = this.DocFrame.cursorToDocumentField("正文",2);
			if (!ret1)
				return false;
			this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJSMpath);  //正文后插入附件说明
			//var value = this.DocFrame.DocObj.DocumentFields("FJ_标题").Value;
			//ret1 = this.DocFrame.setDocumentField("附件说明",value);
			this.DocFrame.setDocumentField("附件",'附件');
			if (!ret1)
				return false;
			
			//删除多余行
			this.DocFrame.cursorToDocumentField("附件说明",2);
			this.DocFrame.DocObj.Application.Selection.MoveDown(5,1,0);  
			for(var k=0; k<2; k++)
			{
				var count = this.DocFrame.DocObj.Application.Selection.Paragraphs.Count;
				for (var i = count;i > 0;i--)
					this.DocFrame.DocObj.Application.Selection.Paragraphs(i).Range.Delete();
			}

			return true;
		
		}
		if (isExists && n == 2)  //模板中有模板
		{
			ret1 = this.renameDocField("附件","附件_1");
			ret2 = this.renameDocField("FJ_标题","FJ_标题_1");
			ret3 = this.renameDocField("FJ_正文","FJ_正文_1");
			if (!ret1 || !ret2 || !ret3)
				return false;
		}
		var FJZW = "FJ_正文_" + (n-1);
		var isExists = this.DocFrame.isExists(FJZW);
		if (!isExists)
			return false;
			
		var ret = this.DocFrame.cursorToDocumentField(FJZW,2);
		if (ret)
		{
			this.DocFrame.DocObj.Application.Selection.InsertBreak();
			this.DocFrame.ActiveDocument.ActiveWindow.Selection.InsertFile(FJpath);
			ret1 = this.renameDocField("附件_0","附件_" + n);
			ret2 = this.renameDocField("FJ_标题_0","FJ_标题_" + n);
			ret3 = this.renameDocField("FJ_正文_0","FJ_正文_" + n);
			if (!ret1 || !ret2 || !ret3)
				return false;
			this.DocFrame.setDocumentField("附件_1",'附件1');
			this.DocFrame.setDocumentField("附件_"+n,'附件'+n);
			//var value = this.DocFrame.DocObj.DocumentFields("FJ_标题_" + n).Value;
			//var retCur = this.DocFrame.cursorToDocumentField("附件说明",4);
			/*if (retCur)
			{
				ret = this.insertParagraph(); 
				if (ret)
					ret = this.DocFrame.insertText(value);
					if(ret)
						return true;
			}*/
		}
		return false;
	}
	catch(e)
	{
		return false;
	}
}
//光标上移一行
Wps.prototype.moveUp = function()
{
	try
	{
		var ret = this.DocFrame.ActiveDocument.Application.Selection.MoveUp();
		if(ret >= 1)
			return true;
		else
			return false;
	}
	catch(e)
	{
		return false;
	}
}
//内容粘贴
Wps.prototype.paste = function(value)
{
	try
	{
		if(value == 1)
		{
			this.DocFrame.ActiveDocument.Application.Selection.PasteAndFormat(16);
		}
		else if(value == 2)
		{
			this.DocFrame.ActiveDocument.Application.Selection.PasteAndFormat(20);
		}
		return true;
	}
	catch(e)
	{
		return false;
	}		
}
//添加表格行
Wps.prototype.addRow = function(value)
{
	try
	{
		if(value == 1)
		{
			this.DocFrame.ActiveDocument.Application.Selection.InsertRowsAbove();
		}
		else if(value == 2)
		{
			this.DocFrame.ActiveDocument.Application.Selection.InsertRowsBelow();
		}
		return true;
	}
	catch(e)
	{
		return false;
	}
}
//删除光标所在表格
Wps.prototype.deleteTable = function()
{
	try
	{
		this.DocFrame.ActiveDocument.Application.Selection.Tables(1).Delete;
		return true;
	}
	catch(e)
	{
		return false;
	}
}
//光标左移
Wps.prototype.moveLeft = function() 
{
	try
	{
		var ret = this.DocFrame.DocObj.Application.Selection.MoveLeft();
		if(ret > 0)
			return true;
		else
			return false;
	}
	catch(e)
	{
		return false;
	}	   
}
//拷贝
Wps.prototype.copy = function() 
{
	try
	{
		this.DocFrame.DocObj.Application.Selection.copy();	
		return true;  			
	}
	catch(e)
	{
		return false;
	}
}
//公文域重命名
Wps.prototype.renameDocField = function(oldName,newdName)
{
	try
	{
		if(newdName != null && newdName != "")
		{
			this.DocFrame.DocObj.DocumentFields(oldName).Name = newdName;
			return true;
		}
		else
			return false;
		
	}
	catch(e)
	{
		return false;
	}
}
//删除光标所在行
Wps.prototype.deleteParagraph = function() 
{
	var i = 0;
	try
	{
		var count = this.DocFrame.DocObj.Application.Selection.Paragraphs.Count;
		for (i = count;i > 0;i--)
			this.DocFrame.DocObj.Application.Selection.Paragraphs(i).Range.Delete();
		return true;      
	}
	catch(e)
	{
		return false;
	}
}
//光标下移
Wps.prototype.moveDown = function()
{
	try
	{
		var ret = this.DocFrame.DocObj.Application.Selection.MoveDown(5,1,0);                   
		if(ret >= 1)
		{
			return true;
		}
		else
		{
			return false ;
		}
	}
	catch(e)
	{
		return false;
	}	   
}
//设置字体
Wps.prototype.setFont = function(type)
{
	try
	{
		var selection=this.DocFrame.DocObj.Application.Selection;                    
		selection.Font.Name=type;
		return true;
	}
	catch(e)
	{
		return false;
	}
}
//删除光标所在行
Wps.prototype.deleteRow = function()
{
	try
	{
		this.DocFrame.DocObj.Application.Selection.Cells.Delete(2); //返回值为undefind 因此没有做判断；
		return true;
	}
	catch(e)
	{
		return false;
	}
}
//接受所有修订
Wps.prototype.acceptAllChanges = function()
{
	try
	{
		this.DocFrame.DocObj.Application.ActiveDocument.AcceptAllRevisions; 
		return true; 
	}
	catch(e)
	{
		return false;
	}
}
Wps.prototype.gevenNumber = function()
{
	try
	{
		this.DocFrame.DocObj.Application.Selection.EndKey(6);
		var page=this.DocFrame.DocObj.Application.Selection.Information(3);
		if ( page%2!=0 ){
			this.DocFrame.cursorToDocumentField("抄送机关_1", 1);
			this.DocFrame.DocObj.Application.Selection.MoveLeft();
			this.DocFrame.DocObj.Application.Selection.MoveLeft();
			this.DocFrame.DocObj.Application.Selection.MoveLeft();
			this.DocFrame.DocObj.Application.Selection.MoveLeft();
			this.DocFrame.DocObj.Application.Selection.InsertBreak();			
		}
	}
	catch(e)
	{
		return false;
	}
}

//linux环境创建对象Wps2
	function Wps2(app)
	{
		this.app = app;
	}		
	Wps2.prototype.moveRight = function()
	{
		try{
			var ret = this.app.ActiveDocument.ActiveWindow.Selection.MoveRight();
			if (ret > 0) 
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
    }
	Wps2.prototype.insertParagraph = function()
	{
		try{
			var ret = this.app.ActiveDocument.ActiveWindow.Selection.TypeParagraph();
			if (ret)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
    }
	Wps2.prototype.setFontSize = function(size)
	{
		try{
			var ret = this.app.ActiveDocument.ActiveWindow.Selection.Font.put_Size(size);
			if (ret)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}		
    }
	
	//////////////////////////////////////////////////////////////////////////////////////
	//插入附件接口
	Wps2.prototype.addAttachment = function(n,fjPath,fjsmPath)//插入新的附件4.
	{
		var ret,ret1,ret2,ret3;
		try
		{
			var isExists = this.app.isExists("附件说明");
			var isExists_2 = this.app.isExists("附注");
			if (n == 1 && !isExists && isExists_2)//模板中没有附件,有附注
			{
				ret = this.app.Selection.EndKey();
				ret = this.app.cursorToDocumentField("附注",2);
				//光标右移
				ret = this.app.ActiveDocument.ActiveWindow.Selection.MoveRight();
				if (ret)
				{
					this.app.Selection.InsertBreak();//undefine
					this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjPath);
					
					ret1 = this.renameDocField("附件_0","附件");
					ret2 = this.renameDocField("FJ_标题_0","FJ_标题");
					ret3 = this.renameDocField("FJ_正文_0","FJ_正文");
					if (!ret1 || !ret2 || !ret3)
						return false;
				
					ret1 = this.app.cursorToDocumentField("正文",2);
					if (!ret1)
						return false;
					this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjsmPath);
					//var fjbtValue = this.app.getDocumentFieldValue("FJ_标题");
					//ret1 = this.app.setDocumentField("附件说明",fjbtValue);
					this.app.setDocumentField("附件",'附件');
					if (!ret1)
						return false;
					//删除多余行
					this.app.cursorToDocumentField("附件说明",2);
					this.app.Selection.MoveDown(5,1,0);
					
					for(var k=0; k<2; k++)
					{		
						var i = 0;
						var paras = this.app.Selection.Paragraphs;
						var count = paras.get_Count();		
						for (i = count;i > 0;i--)
						{
							paras.Item(i).Range.Delete();         
						}
					
					}
					return true;
				}
			}
			if (n == 1 && !isExists && !isExists_2)//模板中没有附件,没有附注
			{
				this.app.Selection.EndKey();
				var isExists_3 = this.app.isExists("成文日期");
				var isExists_4 = this.app.isExists("印发日期");
				if(isExists_3) //模板10和17
				{
					ret = this.app.cursorToDocumentField("成文日期",2);  
					//光标下移
					ret = this.app.Selection.MoveDown(5,1,0); 
					this.app.Selection.InsertBreak();//undefine
					this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjPath);
				}
				else if(!isExists_3 && isExists_4)  //模板7
				{
					this.app.cursorToDocumentField("抄送机关_1",1);
					ret = this.app.Selection.MoveUp();
					ret = this.app.Selection.MoveUp();
					this.app.Selection.InsertBreak();//undefine
					this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjPath);
				}
				else  //模板6
				{
					ret1 = this.app.cursorToDocumentField("正文",2);
					this.app.Selection.InsertBreak();//undefine
					this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjPath);
				}


				ret1 = this.renameDocField("附件_0","附件");
				ret2 = this.renameDocField("FJ_标题_0","FJ_标题");
				ret3 = this.renameDocField("FJ_正文_0","FJ_正文");
				if (!ret1 || !ret2 || !ret3)
					return false;
			
				ret1 = this.app.cursorToDocumentField("正文",2);
				if (!ret1)
					return false;
				
				this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjsmPath);
				//var fjbtValue = this.app.getDocumentFieldValue("FJ_标题");
				//ret1 = this.app.setDocumentField("附件说明",fjbtValue);
				this.app.setDocumentField("附件",'附件');
				if (!ret1)
					return false;
				//删除多余行
				this.app.cursorToDocumentField("附件说明",2);
				this.app.Selection.MoveDown(5,1,0);
				
				for(var k=0; k<2; k++)
				{		
					var i = 0;
					var paras = this.app.Selection.Paragraphs;
					var count = paras.get_Count();		
					for (i = count;i > 0;i--)
					{
						paras.Item(i).Range.Delete();         
					}
				
				}
			
				if(!isExists_3 && isExists_4) /////////////////////////?????????????????????
				{
					ret = this.app.cursorToDocumentField("FJ_正文",2);
					ret = this.app.Selection.MoveDown(5,1,0);  //下移
					ret = this.app.backspace();
					ret = this.app.backspace();
				}
					
				return true;
			}
			if (isExists && n == 2)
			{
				ret1 = this.renameDocField("附件","附件_1");
				ret2 = this.renameDocField("FJ_标题","FJ_标题_1");
				ret3 = this.renameDocField("FJ_正文","FJ_正文_1");
				if (!ret1 || !ret2 || !ret3)
					return false;
			}
			var FJZW = "FJ_正文_" + (n - 1);
			var ret = this.app.cursorToDocumentField(FJZW,2);
			if (ret)
			{
				ret = this.app.Selection.InsertBreak();
				this.app.ActiveDocument.ActiveWindow.Selection.InsertFile(fjPath);
				ret1 = this.renameDocField("附件_0","附件_" + n);
				ret2 = this.renameDocField("FJ_标题_0","FJ_标题_" + n);
				ret3 = this.renameDocField("FJ_正文_0","FJ_正文_" + n);
				if (!ret1 || !ret2 || !ret3)
					return false;
				this.app.setDocumentField("附件_1",'附件1');
				this.app.setDocumentField("附件_"+n,'附件'+n);
				/*var value = this.DocFrame.DocObj.DocumentFields("FJ_标题_" + n).Value;
				var retCur = this.app.cursorToDocumentField("附件说明",4);
				var fjbtValue = this.app.getDocumentFieldValue("FJ_标题_" + n);
				if (retCur)
				{
					ret = this.insertParagraph();
					if (ret)
						ret = this.app.insertText(fjbtValue);
						if(ret)
							return true;
				}*/
			}
			return false;
		}
		catch(e)
		{
			return false;
		}
	}	
	//光标上移一行
	Wps2.prototype.moveUp = function()
	{
		try
		{
			var ret = this.app.Selection.MoveUp();
			if(ret >= 1)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
	}

	//内容粘贴
	Wps2.prototype.paste = function(value)
	{
		try
		{
			if(value == 1)
			{
				var ret = this.app.Selection.PasteAndFormat(16);
			}
			else if(value == 2)
			{
				var ret = this.app.Selection.PasteAndFormat(20);
			}
			if (ret)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
	}

	//添加表格行
	Wps2.prototype.addRow = function(value)
	{
		try
		{
			if(value == 1)
			{
				var ret = this.app.Selection.InsertRowsAbove();
			}
			else if(value == 2)
			{
				var ret = this.app.Selection.InsertRowsBelow();
			}
			if (ret)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
	}
	
	//删除光标所在表格
	Wps2.prototype.deleteTable = function()
	{
		try
		{		
			var ret = this.app.Selection.Tables.Item(1).Delete();
			if (ret)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
	}
	Wps2.prototype.moveLeft = function() 
	{
		try
		{    
			var ret = this.app.Selection.MoveLeft();       
			if(ret > 0)
				return true;
			else
				return false;
		}
		catch(e)
		{
			return false;
		}
	}
	Wps2.prototype.copy = function() 
	{
		try
		{
			this.app.Selection.Copy();
			return true;
		}
		catch(e)
		{
			return false;
		}		
	}

	Wps2.prototype.renameDocField = function(oldName,newName) 
	{
		try
		{
			if(newName != null && newName != "")
			{
				var ret = this.app.ActiveDocument.DocumentFields.Item(oldName).put_Name(newName);
				if (ret)
					return true;
				else
					return false;
			}
			else
			{
			   return false;
			} 
		}
		catch(e)
		{
			return false;
		}
	}	
	Wps2.prototype.deleteParagraph = function() 
	{
		var i = 0;
		try
		{
			var paras = this.app.Selection.Paragraphs;
			var count = paras.get_Count();		
			for (i = count;i > 0;i--)
			{
				paras.Item(i).Range.Delete();         
			}
	       	return true;
		}
        catch(e)
		{
			return false;
        }
	}
	Wps2.prototype.moveDown = function()
	{
		try
		{
			var ret = this.app.Selection.MoveDown(5,1,0);                          
			if(ret >= 1)
				return true;
	  		else
	          	return false ;
		}
		catch(e)
		{
		 	return false;
	 	}	   
	}
	Wps2.prototype.setFont = function(type)
	{
		try
		{
			this.app.Selection.Font.Name=type;
			return true;
		}
		catch(e)
		{
			return false;
		}
	}
	Wps2.prototype.deleteRow = function()
	{
		try
		{
			this.app.Selection.Cells.Delete(2);
			return true;
		}
		catch(e)
		{
			return false;
		}
	}
	//接受所有修订
	Wps2.prototype.acceptAllChanges = function()
	{
		try
		{
			this.app.ActiveDocument.AcceptAllRevisions();				
			return true;
		}
		catch(e)
		{
			return false;
		}
	}
	Wps2.prototype.gevenNumber = function()
	{
		try
		{
			obj.Application.Selection.EndKey(6);
			var page=obj.Application.Selection.get_Information(3);
			if ( page%2!=0 ){
				if(this.app.isExists("抄送机关")){
					this.app.cursorToDocumentField("抄送机关", 1);
				}else{
					this.app.cursorToDocumentField("抄送机关_1", 1);
				}
				
				this.app.ActiveDocument.ActiveWindow.Selection.MoveLeft();              
				this.app.ActiveDocument.ActiveWindow.Selection.MoveLeft();    
				this.app.ActiveDocument.ActiveWindow.Selection.MoveLeft();    
				this.app.ActiveDocument.ActiveWindow.Selection.MoveLeft();
				this.app.ActiveDocument.ActiveWindow.Selection.InsertBreak();	
			}
		}
		catch(e)
		{
			return false;
		}
	}
