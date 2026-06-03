import React, { createContext, useContext, useState } from 'react';
import { jwtDecode } from 'jwt-decode';

interface AuthContextType {
  user: string | null;
  token: string | null;
  role: string | null;
  login: (username: string, token: string, role: string) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<string | null>(localStorage.getItem('username'));
  const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
  const [role, setRole] = useState<string | null>(localStorage.getItem('role'));

  const login = (username: string, userToken: string, userRole: string) => {
    try {
      const decoded: any = jwtDecode(userToken);
      
      const finalRole = decoded.roles?.[0] || decoded.authorities?.[0] || userRole;

      localStorage.setItem('token', userToken);
      localStorage.setItem('username', username);
      localStorage.setItem('role', finalRole);
      
      setUser(username);
      setToken(userToken);
      setRole(finalRole);
    } catch (error) {
      console.error("Greška pri dekodiranju tokena, radim fallback na osnovne parametre:", error);
      
      localStorage.setItem('token', userToken);
      localStorage.setItem('username', username);
      localStorage.setItem('role', userRole);
      
      setUser(username);
      setToken(userToken);
      setRole(userRole);
    }
  };

  const logout = () => {
    localStorage.removeItem('username');
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    setUser(null);
    setToken(null);
    setRole(null);
  };

  const isAuthenticated = !!token;

  return (
    <AuthContext.Provider value={{ user, token, role, login, logout, isAuthenticated }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};