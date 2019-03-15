#! /usr/bin/env python
# encoding: utf-8
import os.path

def options(opt):
  opt.load('java')

def configure(conf):
  conf.load('java')

def build(bld):
  bld(rule = 'git clone https://github.com/emina/kodkod')
  bld.add_group()
  bld(rule = 'cd kodkod && waf configure --prefix=. --libdir=lib build install')
  bld.add_group()
  bld(rule = 'cp kodkod/lib/kodkod.jar .', target = 'kodkod.jar')
  bld.add_group()

  bld(features  = 'javac jar',
      name      = 'meow',
      srcdir    = 'src',
      outdir    = 'meow',
      compat    = '1.8',
      classpath = ['.', 'kodkod/lib/kodkod.jar'],
      manifest  = 'src/MANIFEST',
      basedir   = 'meow',
      destfile  = 'meow.jar')

  bld(features  = 'javac jar',
      name      = 'benchmarks',
      use       = 'meow',
      srcdir    = 'benchmarks',
      outdir    = 'benchmarks',
      compat    = '1.8',
      classpath = ['.', 'kodkod/lib/kodkod.jar', 'meow.jar'],
      manifest  = 'benchmarks/MANIFEST',
      basedir   = 'benchmarks',
      destfile  = 'benchmarks.jar')

  bld.install_files('bin', ['meow.jar', 'benchmarks.jar', 'kodkod.jar'])

def distclean(ctx):
  from waflib import Scripting
  Scripting.distclean(ctx)
